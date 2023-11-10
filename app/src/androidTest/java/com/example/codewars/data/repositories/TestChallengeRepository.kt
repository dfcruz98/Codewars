package com.example.codewars.data.repositories

import androidx.paging.PagingData
import com.example.codewars.data.models.Challenge
import com.example.codewars.data.models.CompletedChallenge
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


