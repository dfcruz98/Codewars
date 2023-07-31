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
import com.cocus.codewars.data.remote.api.CodewarsApi
import com.cocus.codewars.data.remote.utils.RequestResult
import com.cocus.codewars.data.remote.utils.tryMakingRequest
import com.cocus.codewars.data.toCompletedChallengeEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CompletedChallengesMediator @Inject constructor(
    private val user: String,
    private val database: CodewarsDatabase,
    private val api: CodewarsApi,
) : RemoteMediator<Int, CompletedChallengeEntity>() {

    private val completedChallengesDao = database.completedChallengesDao()
    private val completedChallengesPagesDao = database.completedChallengesPagesDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CompletedChallengeEntity>
    ): MediatorResult {

        Log.d("CompletedChallengesMediator", "loadType=$loadType")

        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 0
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.previousPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevPage
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextPage
            }
        }

        return when (val response =
            tryMakingRequest { api.getCompletedChallenges(user, currentPage) }
        ) {
            is RequestResult.Error -> {
                return MediatorResult.Error(Throwable())
            }

            is RequestResult.Exception -> {
                return MediatorResult.Error(response.ex)
            }

            is RequestResult.Success -> {

                val endOfPaginationReached = response.value.data.isEmpty()

                val prevPage = if (currentPage == 0) null else currentPage - 1
                val nextPage = if (endOfPaginationReached) null else currentPage + 1

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        completedChallengesDao.deleteAll()
                        completedChallengesPagesDao.deleteAll()
                    }

                    val challenges = mutableListOf<CompletedChallengeEntity>()
                    val pages = mutableListOf<CompletedChallengePageEntity>()

                    response.value.data.forEach { challenge ->
                        challenges.add(challenge.toCompletedChallengeEntity(user))
                        pages.add(
                            CompletedChallengePageEntity(
                                challengeId = challenge.id,
                                previousPage = prevPage,
                                nextPage = nextPage
                            )
                        )
                    }

                    completedChallengesDao.insert(challenges)
                    completedChallengesPagesDao.insert(pages)
                }

                MediatorResult.Success(
                    endOfPaginationReached = endOfPaginationReached
                )
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CompletedChallengeEntity>
    ): CompletedChallengePageEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                completedChallengesPagesDao.get(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CompletedChallengeEntity>
    ): CompletedChallengePageEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { challenge ->
                completedChallengesPagesDao.get(id = challenge.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CompletedChallengeEntity>
    ): CompletedChallengePageEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { challenge ->
                completedChallengesPagesDao.get(id = challenge.id)
            }
    }

}