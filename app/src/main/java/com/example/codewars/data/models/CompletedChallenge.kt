package com.example.codewars.data.models

data class CompletedChallenge(
    val id: String,
    val name: String,
    val slug: String,
    val completedAt: String,
    val completedLanguages: List<String>,
)