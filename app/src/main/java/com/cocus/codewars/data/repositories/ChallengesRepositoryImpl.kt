package com.cocus.codewars.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.cocus.codewars.data.local.CodewarsDatabase
import com.cocus.codewars.data.paging.CompletedChallengesMediator
import com.cocus.codewars.data.remote.services.CodewarsApi
import com.cocus.codewars.data.toCompletedChallenge
import com.cocus.codewars.domain.models.CompletedChallenge
import com.cocus.codewars.domain.repositories.ChallengesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ChallengesRepositoryImpl @Inject constructor(
    private val codewarsApi: CodewarsApi,
    private val database: CodewarsDatabase,
) : ChallengesRepository {

    override fun getCompletedChallenges(user: String): Flow<PagingData<CompletedChallenge>> {
        val pagingSourceFactory = { database.challengesDao().pagingSource() }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = CompletedChallengesMediator(
                user = user,
                database = database,
                service = codewarsApi,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { paging -> paging.map { it.toCompletedChallenge() } }
    }

}