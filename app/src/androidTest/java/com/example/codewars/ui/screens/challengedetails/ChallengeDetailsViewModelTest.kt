package com.example.codewars.ui.screens.challengedetails

import androidx.lifecycle.SavedStateHandle
import com.example.codewars.data.models.ApprovedBy
import com.example.codewars.data.models.Challenge
import com.example.codewars.data.models.CreatedBy
import com.example.codewars.data.models.Rank
import com.example.codewars.data.repositories.TestChallengeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChallengeDetailsViewModelTest {

    private lateinit var viewModel: ChallengeDetailsViewModel
    private val repository = TestChallengeRepository()

    private val challenge = Challenge(
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

    @Before
    fun setup() {
        viewModel = ChallengeDetailsViewModel(
            savedStateHandle = SavedStateHandle(mapOf(challengeDetailsId to "5277c8a221e209d3f6000b56")),
            repository = repository
        )
    }

    @Test
    fun `check initial state`() = runTest {
        assertThat(viewModel.challenge.value)
            .isInstanceOf(ChallengeUiState.Loading::class.java)
    }

    @Test
    fun `challenge found state`() = runTest {
        backgroundScope.launch(Dispatchers.Unconfined) {
            viewModel.challenge.collect()
        }

        assertThat(viewModel.challenge.value)
            .isInstanceOf(ChallengeUiState.Loading::class.java)

        repository.emi(challenge)

        val challenge = viewModel.challenge.value
        assertThat(challenge).isInstanceOf(ChallengeUiState.Success::class.java)
    }

    @Test
    fun `challenge not found state`() = runTest {
        backgroundScope.launch(Dispatchers.Unconfined) {
            viewModel.challenge.collect()
        }

        assertThat(viewModel.challenge.value)
            .isInstanceOf(ChallengeUiState.Loading::class.java)

        repository.emi(null)

        val challenge = viewModel.challenge.value
        assertThat(challenge).isInstanceOf(ChallengeUiState.NotFound::class.java)
    }
}