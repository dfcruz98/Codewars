package com.example.codewars.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codewars.data.local.entities.CompletedChallengePageEntity

@Dao
interface CompletedChallengesPagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pages: List<CompletedChallengePageEntity>)

    @Query("SELECT * FROM completed_challenges_pages WHERE challengeId = :id")
    suspend fun get(id: String): CompletedChallengePageEntity

    @Delete
    suspend fun delete(page: CompletedChallengePageEntity)

    @Query("DELETE FROM completed_challenges_pages")
    suspend fun deleteAll()
}