package com.example.codewars.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class ChallengeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val slug: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>,
    @Embedded(prefix = "rank_")
    val rank: RankEntity,
    @Embedded(prefix = "created_")
    val createdBy: CreatedByEntity,
    @Embedded(prefix = "approved_")
    val approvedBy: ApprovedByEntity,
    val totalAttempts: Long,
    val totalCompleted: Long,
    val totalStars: Long,
    val voteScore: Long,
    val publishedAt: String,
    val approvedAt: String,
)

data class RankEntity(
    val id: Long,
    val name: String,
    val color: String,
)

data class CreatedByEntity(
    val username: String,
    val url: String,
)

data class ApprovedByEntity(
    val username: String,
    val url: String,
)