package com.cocus.codewars.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.cocus.codewars.data.local.entities.CompletedChallengeEntity

@Dao
interface CompletedChallengesDao {

    @Upsert
    suspend fun insert(challenges: List<CompletedChallengeEntity>)

    @Query("SELECT * FROM completed_challenges WHERE id = :id")
    suspend fun get(id: String): CompletedChallengeEntity?

    @Query("SELECT * FROM completed_challenges")
    fun pagingSource(): PagingSource<Int, CompletedChallengeEntity>

    @Query("DELETE FROM completed_challenges")
    suspend fun deleteAll()
}