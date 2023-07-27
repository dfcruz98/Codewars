package com.cocus.codewars.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cocus.codewars.data.local.entities.CompletedChallengePageEntity

@Dao
interface CompletedChallengesPagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(pages: List<CompletedChallengePageEntity>)

    @Query("SELECT * FROM completed_challenges_pages WHERE user = :user")
    suspend fun remoteKeyByQuery(user: String): CompletedChallengePageEntity

    @Query("SELECT * FROM completed_challenges_pages WHERE id = :id")
    suspend fun remoteKeyById(id: String): CompletedChallengePageEntity

    @Query("DELETE FROM completed_challenges_pages WHERE user = :user")
    suspend fun deleteByQuery(user: String)

    @Query("DELETE FROM completed_challenges_pages")
    suspend fun deleteAll()
}