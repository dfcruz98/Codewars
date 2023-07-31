package com.cocus.codewars.ui.screens.challenges

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cocus.codewars.data.models.CompletedChallenge


const val completedChallengesNavigationRoute = "completed_challenges"

fun NavController.navigateToCompletedChallenges(navOptions: NavOptions? = null) {
    this.navigate(completedChallengesNavigationRoute, navOptions)
}

fun NavGraphBuilder.completedChallengesScreen(
    onClick: (CompletedChallenge) -> Unit,
) {
    composable(
        route = completedChallengesNavigationRoute,
    ) {
        CompletedChallengesRoute(onClick)
    }
}