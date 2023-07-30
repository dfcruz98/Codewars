package com.cocus.codewars.ui.screens.challengedetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val challengeDetailsId = "challengeId"
const val challengeDetailsNavigationRoute = "challenge/{challengeId}"

fun NavController.navigateToChallengeDetails(
    challengeId: String,
    navOptions: NavOptions? = null
) {
    this.navigate("challenge/$challengeId", navOptions)
}

fun NavGraphBuilder.challengesDetailsScreen() {
    composable(
        route = challengeDetailsNavigationRoute,
        arguments = listOf(
            navArgument(challengeDetailsId) { type = NavType.StringType },
        ),
    ) {
        ChallengeDetailsRoute()
    }
}