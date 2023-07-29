package com.cocus.codewars.testrepositories

import androidx.paging.PagingData
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.models.CompletedChallenge
import com.cocus.codewars.domain.repositories.ChallengesRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestChallengeRepository : ChallengesRepository {

    private val challengesFlow: MutableSharedFlow<List<Challenge>> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun getCompletedChallenges(user: String): Flow<PagingData<CompletedChallenge>> {
        private val items = (0..100).map(Any::toString)

        private val pagingSourceFactory = items.asPagingSourceFactory()

         pagingSourceFactory()

    }

    override fun getChallenge(name: String): Flow<Challenge> {
        return challengesFlow.map { challenge -> challenge.find { it.name == name }!! }
    }

    override suspend fun refreshChallenge(name: String) {
        TODO("Not yet implemented")
    }

    fun sendChallenge(topics: List<Challenge>) {
        challengesFlow.tryEmit(topics)
    }
}


