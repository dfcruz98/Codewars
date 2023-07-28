package com.cocus.codewars.domain.repositories

import androidx.paging.PagingData
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.models.CompletedChallenge
import kotlinx.coroutines.flow.Flow

interface ChallengesRepository {

    fun getCompletedChallenges(user: String): Flow<PagingData<CompletedChallenge>>

    fun getChallenge(name: String): Flow<Challenge?>

    suspend fun refreshChallenge(name: String)

}