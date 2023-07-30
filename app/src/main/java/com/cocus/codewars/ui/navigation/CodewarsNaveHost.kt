package com.cocus.codewars.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cocus.codewars.ui.screens.challengedetails.challengesDetailsScreen
import com.cocus.codewars.ui.screens.challengedetails.navigateToChallengeDetails
import com.cocus.codewars.ui.screens.challenges.completedChallengesNavigationRoute
import com.cocus.codewars.ui.screens.challenges.completedChallengesScreen

@Composable
fun CodewarsNaveHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = completedChallengesNavigationRoute,
        modifier = modifier,
    ) {
        completedChallengesScreen { challenge ->
            navController.navigateToChallengeDetails(challenge.id)
        }
        challengesDetailsScreen()
    }
}
