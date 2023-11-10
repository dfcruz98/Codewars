package com.example.codewars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.codewars.data.local.dao.ChallengeDao
import com.example.codewars.data.local.dao.CompletedChallengesDao
import com.example.codewars.data.local.dao.CompletedChallengesPagesDao
import com.example.codewars.data.local.entities.ChallengeEntity
import com.example.codewars.data.local.entities.CompletedChallengeEntity
import com.example.codewars.data.local.entities.CompletedChallengePageEntity
import com.example.codewars.data.local.entities.converters.Converters

const val DATABASE_NAME = "Codewars"

@Database(
    entities = [
        CompletedChallengeEntity::class,
        CompletedChallengePageEntity::class,
        ChallengeEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CodewarsDatabase : RoomDatabase() {

    abstract fun challengeDao(): ChallengeDao

    abstract fun completedChallengesDao(): CompletedChallengesDao

    abstract fun completedChallengesPagesDao(): CompletedChallengesPagesDao
}