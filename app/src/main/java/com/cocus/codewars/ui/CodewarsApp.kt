package com.cocus.codewars.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cocus.codewars.ui.components.CodewarsTopBar
import com.cocus.codewars.ui.navigation.CodewarsNaveHost
import com.cocus.codewars.ui.navigation.TopLevelDestination
import com.cocus.codewars.ui.screens.challengedetails.challengeDetailsNavigationRoute
import com.cocus.codewars.ui.screens.challenges.completedChallengesNavigationRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodewarsApp(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                getCurrentDestination(navController)?.let { destination ->
                    CodewarsTopBar(
                        titleRes = destination.title,
                        goBack = destination.goBack,
                        onNavigationClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        ) { paddings ->
            CodewarsNaveHost(
                navController = navController,
                modifier = Modifier.padding(paddings),
            )
        }
    }
}

@Composable
fun getCurrentDestination(navController: NavController): TopLevelDestination? {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination
    return when (currentDestination?.route) {
        challengeDetailsNavigationRoute -> {
            TopLevelDestination.CHALLENGE_DETAILS
        }

        completedChallengesNavigationRoute -> {
            TopLevelDestination.COMPLETED_CHALLENGES
        }

        else -> null
    }
}