package com.cocus.codewars.ui.screens.challengedetails

import androidx.lifecycle.SavedStateHandle
import com.cocus.codewars.domain.models.ApprovedBy
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.models.CreatedBy
import com.cocus.codewars.domain.models.Rank
import com.cocus.codewars.testrepositories.TestChallengeRepository
import com.google.common.truth.Truth
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
            savedStateHandle = SavedStateHandle(),
            repository = repository
        )
    }

    @Test
    fun testInitialStateIsLoading() = runTest {
        Truth.assertThat(viewModel.challenge).isEqualTo(ChallengeUiState.Loading)
    }

    @Test
    fun testGetChallengeValidParameter() = runTest {
        val collectJob = launch(Dispatchers.Unconfined) { viewModel.challenge.collect() }

        repository.sendChallenge(listOf(challenge))

        val challenge = viewModel.challenge.value
        Truth.assertThat(challenge).isEqualTo(ChallengeUiState.Success::class)

        collectJob.cancel()
    }

    @Test
    fun testGetChallengeEmptyParameter() = runTest {
        val collectJob = launch(Dispatchers.Unconfined) { viewModel.challenge.collect() }

        repository.sendChallenge(listOf())

        val challenge = viewModel.challenge.value
        Truth.assertThat(challenge).isEqualTo(ChallengeUiState.Loading::class)

        collectJob.cancel()
    }
}