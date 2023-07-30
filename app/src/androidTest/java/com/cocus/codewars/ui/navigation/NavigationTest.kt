package com.cocus.codewars.ui.navigation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.cocus.codewars.MainActivity
import com.cocus.codewars.ui.screens.challengedetails.challengeDetailsNavigationRoute
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CodewarsNaveHost(navController = navController)
        }
    }

    @Test
    fun `verify start destination`() {
        composeTestRule
            .onNodeWithTag("CompletedChallengesBox")
            .assertIsDisplayed()
    }

    @Test
    fun `navigate to challenge details`() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("CompletedChallengesItem")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("CompletedChallengesItem")
            .onFirst()
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertThat(route).isEqualTo(challengeDetailsNavigationRoute)
    }
}