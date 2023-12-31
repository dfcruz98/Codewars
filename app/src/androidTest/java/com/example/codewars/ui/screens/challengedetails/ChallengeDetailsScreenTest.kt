package com.example.codewars.ui.screens.challengedetails

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.codewars.MainActivity
import com.example.codewars.data.models.ApprovedBy
import com.example.codewars.data.models.Challenge
import com.example.codewars.data.models.CreatedBy
import com.example.codewars.data.models.Rank
import org.junit.Rule
import org.junit.Test

class ChallengeDetailsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `loading state`() {
        composeTestRule.activity.setContent {
            ChallengesDetailsScreen(
                uiState = ChallengeUiState.Loading,
            )
        }

        composeTestRule
            .onNodeWithTag("ChallengeDetailLoading")
            .assertIsDisplayed()
    }

    @Test
    fun `show challenge details`() {
        composeTestRule.activity.setContent {
            ChallengesDetailsScreen(
                uiState = ChallengeUiState.Success(
                    Challenge(
                        id = "5277c8a221e209d3f6000b56",
                        name = "Valid Braces",
                        slug = "valid-braces",
                        url = "http://www.codewars.com/kata/valid-braces",
                        category = "algorithms",
                        description = "Write a function called `validBraces` that takes a string ...",
                        tags = listOf("Algorithms", "Validation", "Logic", "Utilities"),
                        languages = listOf("javascript", "coffeescript"),
                        rank = Rank(
                            id = -4,
                            name = "4 kyu",
                            color = "blue",
                        ),
                        createdBy = CreatedBy(
                            username = "xDranik",
                            url = "http://www.codewars.com/users/xDranik",
                        ),
                        approvedBy = ApprovedBy(
                            username = "xDranik",
                            url = "http://www.codewars.com/users/xDranik",
                        ),
                        totalAttempts = 4911,
                        totalCompleted = 919,
                        totalStars = 12,
                        voteScore = 512,
                        publishedAt = "2013-11-05T00:07:31Z",
                        approvedAt = "013-12-20T14:53:06Z",
                    )
                ),
            )
        }

        composeTestRule
            .onNodeWithTag("ChallengeDetailLoading")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("ChallengeDetailsView")
            .assertIsDisplayed()
    }

}