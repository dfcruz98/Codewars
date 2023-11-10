package com.example.codewars.ui.navigation

import com.example.codewars.R

enum class TopLevelDestination(
    val goBack: Boolean = false,
    val title: Int,
) {
    COMPLETED_CHALLENGES(
        title = R.string.completed_challenges,
    ),
    CHALLENGE_DETAILS(
        goBack = true,
        title = R.string.challenge_details,
    ),
}
