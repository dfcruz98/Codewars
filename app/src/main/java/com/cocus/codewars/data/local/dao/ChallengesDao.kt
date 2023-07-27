package com.cocus.codewars.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.cocus.codewars.data.local.entities.CompletedChallengeEntity

@Dao
interface ChallengesDao {

    @Upsert
    suspend fun upsertCompletedChallenges(challenges: List<CompletedChallengeEntity>)

    @Query("SELECT * FROM completed_challenges")
    fun pagingSource(): PagingSource<Int, CompletedChallengeEntity>

    @Query("DELETE FROM completed_challenges")
    suspend fun deleteAll()
}