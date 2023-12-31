package com.example.codewars.data.repositories

import androidx.paging.PagingData
import com.example.codewars.data.models.Challenge
import com.example.codewars.data.models.CompletedChallenge
import kotlinx.coroutines.flow.Flow

interface ChallengesRepository {

    fun getCompletedChallenges(user: String): Flow<PagingData<CompletedChallenge>>

    fun getChallenge(name: String): Flow<Challenge?>

    suspend fun refreshChallenge(name: String)

}