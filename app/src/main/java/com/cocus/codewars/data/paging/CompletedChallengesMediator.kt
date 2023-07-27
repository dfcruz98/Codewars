package com.cocus.codewars.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cocus.codewars.data.local.CodewarsDatabase
import com.cocus.codewars.data.local.entities.CompletedChallengeEntity
import com.cocus.codewars.data.local.entities.CompletedChallengePageEntity
import com.cocus.codewars.data.remote.services.CodewarsApi
import com.cocus.codewars.data.toCompletedChallengeEntity
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CompletedChallengesMediator @Inject constructor(
    private val user: String,
    private val database: CodewarsDatabase,
    private val service: CodewarsApi,
) : RemoteMediator<Int, CompletedChallengeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CompletedChallengeEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    getLastPage(state)?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
            }

            val request = service.getCompletedChallenges(user, currentPage)

            val prevPage = if (currentPage == 0) null else currentPage - 1
            val nextPage = if (request.totalPages == currentPage) null else currentPage + 1

            Log.d(
                "CompletedChallengesMediator",
                "currentPage=$currentPage, prevPage=$prevPage, nextPage=$nextPage"
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.challengesDao().deleteAll()
                    database.completedChallengesPagesDao().deleteAll()
                }

                val challenges = mutableListOf<CompletedChallengeEntity>()
                val pages = mutableListOf<CompletedChallengePageEntity>()

                request.data.forEach {
                    challenges.add(it.toCompletedChallengeEntity(user))
                    pages.add(
                        CompletedChallengePageEntity(
                            user = user,
                            previousPage = prevPage,
                            nextPage = nextPage
                        )
                    )
                }

                database.challengesDao().upsertCompletedChallenges(challenges)
                database.completedChallengesPagesDao().insertOrReplace(pages)
            }

            MediatorResult.Success(
                endOfPaginationReached = request.totalItems == request.totalPages
            )
        } catch (io: IOException) {
            return MediatorResult.Error(io)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }

    private suspend fun getLastPage(
        state: PagingState<Int, CompletedChallengeEntity>
    ): CompletedChallengePageEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.completedChallengesPagesDao().remoteKeyById(id = it.id)
            }
    }

}