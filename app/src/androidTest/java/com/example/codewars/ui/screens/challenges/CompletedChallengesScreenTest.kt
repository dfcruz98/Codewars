package com.example.codewars.ui.screens.challenges

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import com.example.codewars.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CompletedChallengesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            CompletedChallengesRoute(
                onClick = {}
            )
        }
    }

    @Test
    fun `check loading spinner`() {
        composeTestRule
            .onNodeWithTag("CompletedChallengesLoading")
            .assertIsDisplayed()
    }

    @Test
    fun `check scroll up button not visible`() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("CompletedChallengesItem")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag("CompletedChallengesList")
            .performScrollToIndex(0)

        composeTestRule
            .onNodeWithTag("ScrollUpButton")
            .assertDoesNotExist()
    }

    @Test
    fun `check is scroll up button is displayed after scrolling`() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("CompletedChallengesItem")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag("CompletedChallengesList")
            .performScrollToIndex(1)

        composeTestRule
            .onNodeWithTag("ScrollUpButton")
            .assertIsDisplayed()
    }
}