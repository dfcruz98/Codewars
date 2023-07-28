package com.cocus.codewars.domain.models

data class Challenge(
    val id: String,
    val name: String,
    val slug: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>,
    val rank: Rank,
    val createdBy: CreatedBy,
    val approvedBy: ApprovedBy,
    val totalAttempts: Long,
    val totalCompleted: Long,
    val totalStars: Long,
    val voteScore: Long,
    val publishedAt: String,
    val approvedAt: String,
)

data class Rank(
    val id: Long,
    val name: String,
    val color: String,
)

data class CreatedBy(
    val username: String,
    val url: String,
)

data class ApprovedBy(
    val username: String,
    val url: String,
)
