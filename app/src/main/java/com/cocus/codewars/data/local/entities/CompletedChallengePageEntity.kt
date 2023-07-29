package com.cocus.codewars.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_challenges_pages")
class CompletedChallengePageEntity(
    @PrimaryKey(autoGenerate = false)
    val challengeId: String,
    val previousPage: Int?,
    val nextPage: Int?,
)