package com.example.codewars.data.repositories

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.codewars.data.local.CodewarsDatabase
import com.example.codewars.data.paging.CompletedChallengesMediator
import com.example.codewars.data.remote.api.CodewarsApi
import com.example.codewars.data.remote.utils.RequestResult
import com.example.codewars.data.remote.utils.tryMakingRequest
import com.example.codewars.data.toChallenge
import com.example.codewars.data.toChallengeEntity
import com.example.codewars.data.toCompletedChallenge
import com.example.codewars.data.models.Challenge
import com.example.codewars.data.models.CompletedChallenge
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
        return Pager(
            config = PagingConfig(pageSize = 200),
            remoteMediator = CompletedChallengesMediator(
                user = user,
                database = database,
                api = codewarsApi,
            ),
            pagingSourceFactory = { database.completedChallengesDao().pagingSource() }
        )
            .flow
            .map { paging ->
                paging.map { it.toCompletedChallenge() }
            }
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
        when (val result = tryMakingRequest { codewarsApi.getChallenge(name) }) {
            is RequestResult.Error -> {
                Log.e("ChallengesRepositoryImpl", "message: ${result.message}")
            }

            is RequestResult.Exception -> {
                Log.e("ChallengesRepositoryImpl", "message: ${result.ex.message}")
            }

            is RequestResult.Success -> {
                result.value
                    .toChallengeEntity()
                    .also { challenge ->
                        challenge?.let {
                            database.challengeDao().insert(challenge)
                        }
                    }
            }
        }

    }

}