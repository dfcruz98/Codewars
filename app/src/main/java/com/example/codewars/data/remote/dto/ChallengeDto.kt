package com.example.codewars.data.remote.dto

data class ChallengeDto(
    val id: String,
    val name: String,
    val slug: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>,
    val rank: RankDto,
    val createdBy: CreatedByDto,
    val approvedBy: ApprovedByDto,
    val totalAttempts: Long,
    val totalCompleted: Long,
    val totalStars: Long,
    val voteScore: Long,
    val publishedAt: String,
    val approvedAt: String,
)

data class RankDto(
    val id: Long,
    val name: String,
    val color: String,
)

data class CreatedByDto(
    val username: String,
    val url: String,
)

data class ApprovedByDto(
    val username: String,
    val url: String,
)
