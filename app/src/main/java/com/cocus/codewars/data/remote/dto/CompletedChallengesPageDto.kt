package com.cocus.codewars.data.remote.dto


data class CompletedChallengesPageDto(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<CompletedChallengeDto>,
)

data class CompletedChallengeDto(
    val id: String,
    val name: String,
    val slug: String,
    val completedAt: String,
    val completedLanguages: List<String>,
)