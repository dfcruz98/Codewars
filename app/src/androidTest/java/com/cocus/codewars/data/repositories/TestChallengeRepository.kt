package com.cocus.codewars.data.repositories

import androidx.paging.PagingData
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.models.CompletedChallenge
import com.cocus.codewars.domain.repositories.ChallengesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestChallengeRepository : ChallengesRepository {

    private val flow = MutableSharedFlow<Challenge?>()


    override fun getCompletedChallenges(user: String): Flow<PagingData<CompletedChallenge>> {
        TODO()
    }

    override fun getChallenge(name: String): Flow<Challenge?> = flow

    override suspend fun refreshChallenge(name: String) {
        TODO()
    }

    suspend fun emi(topic: Challenge?) = flow.emit(topic)

}


