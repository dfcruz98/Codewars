package com.example.codewars.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_challenges")
data class CompletedChallengeEntity(
    @PrimaryKey
    val id: String,
    val user: String,
    val name: String,
    val slug: String,
    val completedAt: String,
    @ColumnInfo(name = "completed_languages")
    var completedLanguages: List<String>,
)