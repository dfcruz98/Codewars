package com.cocus.codewars.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.cocus.codewars.data.local.CodewarsDatabase
import com.cocus.codewars.data.paging.CompletedChallengesMediator
import com.cocus.codewars.data.remote.services.CodewarsApi
import com.cocus.codewars.data.toChallenge
import com.cocus.codewars.data.toChallengeEntity
import com.cocus.codewars.data.toCompletedChallenge
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.models.CompletedChallenge
import com.cocus.codewars.domain.repositories.ChallengesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ChallengesRepositoryImpl @Inject constructor(
    private val codewarsApi: CodewarsApi,
    private val database: CodewarsDatabase,
) : ChallengesRepository {

    override fun getCompletedChallenges(user: String): Flow<PagingData<CompletedChallenge>> {
        val pagingSourceFactory = { database.completedChallengesDao().pagingSource() }
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

    override fun getChallenge(name: String): Flow<Challenge?> {
        return database.challengeDao()
            .get(name)
            .map { challenge -> challenge?.toChallenge() }
            .onEach { challenge ->
                if (challenge == null) {
                    refreshChallenge(name)
                }
            }
    }

    override suspend fun refreshChallenge(name: String) {
        val result = codewarsApi.getChallenge(name)
        result
            .toChallengeEntity()
            .also { challenge -> database.challengeDao().insert(challenge) }
    }

}