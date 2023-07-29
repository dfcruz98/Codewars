package com.cocus.codewars.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cocus.codewars.data.local.entities.ChallengeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {

    @Query("SELECT * FROM challenges WHERE id = :id")
    fun get(id: String): Flow<ChallengeEntity?>

    @Query("SELECT * FROM challenges")
    fun getAll(): Flow<ChallengeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: ChallengeEntity)

    @Update
    suspend fun update(challenge: ChallengeEntity)

    @Delete
    suspend fun delete(challenge: ChallengeEntity)

    @Query("DELETE FROM challenges")
    suspend fun clearAll()
}