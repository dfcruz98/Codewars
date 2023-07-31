package com.cocus.codewars.ui.screens.challengedetails

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cocus.codewars.R
import com.cocus.codewars.domain.models.ApprovedBy
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.models.CreatedBy
import com.cocus.codewars.domain.models.Rank
import com.cocus.codewars.ui.components.CodewarsRank
import com.cocus.codewars.ui.components.MarkdownText

@Composable
fun ChallengeDetailsRoute(
    viewModel: ChallengeDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.challenge.collectAsStateWithLifecycle()
    ChallengesDetailsScreen(uiState.value)
}

@Composable
fun ChallengesDetailsScreen(
    uiState: ChallengeUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (uiState) {
            ChallengeUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("ChallengeDetailLoading")
                )
            }

            is ChallengeUiState.Success -> {
                uiState.challenge?.let { challenge ->
                    ChallengeDetails(challenge)
                }
            }

            else -> {
                Log.d("ChallengesDetailsScreen", "uiState=$uiState")
            }
        }
    }
}

@Composable
private fun ChallengeDetails(
    challenge: Challenge,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
            .testTag("ChallengeDetailsView")
    ) {
        Row {
            CodewarsRank(challenge.rank.name, Color.Cyan)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = challenge.name,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(imageVector = Icons.Filled.Star, contentDescription = "")
            Text(
                text = challenge.totalStars.toString(),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = ""
            )
            Text(text = challenge.createdBy.username)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = Icons.Filled.Tag, contentDescription = "")
            Text(
                text = challenge.tags.joinToString(", "),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))

        MarkdownText(
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            markdown = challenge.description,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChallengesDetailsScreenPreview() {
    ChallengesDetailsScreen(
        ChallengeUiState.Success(
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
        )
    )
}