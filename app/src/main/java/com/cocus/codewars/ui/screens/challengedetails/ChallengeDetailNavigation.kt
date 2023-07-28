package com.cocus.codewars.ui.screens.challengedetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val challengeDetailName = "name"
const val challengeDetailsNavigationRoute = "challenge/{name}"

fun NavController.navigateToChallengeDetails(navOptions: NavOptions? = null) {
    this.navigate(challengeDetailsNavigationRoute, navOptions)
}

fun NavGraphBuilder.challengesDetailsScreen() {
    composable(
        route = challengeDetailsNavigationRoute,
    ) { backStackEntry ->
        ChallengeDetailsRoute(backStackEntry.arguments?.getString(challengeDetailName))
    }
}