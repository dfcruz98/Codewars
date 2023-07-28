package com.cocus.codewars.ui.screens.challengedetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cocus.codewars.domain.models.ApprovedBy
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.models.CreatedBy
import com.cocus.codewars.domain.models.Rank

@Composable
fun ChallengeDetailsRoute(
    challengeName: String?,
    viewModel: ChallengeDetailsViewModel = hiltViewModel()
) {
    challengeName?.let {
        viewModel.getChallenge(it)
    }
    val challenge = viewModel.challenge.collectAsStateWithLifecycle()
    challenge.value?.let {
        ChallengesDetailsScreen(it)
    }
}

@Composable
internal fun ChallengesDetailsScreen(
    challenge: Challenge,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Card {
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Level(challenge.rank.name)
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = challenge.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Filled.PanoramaFishEye, contentDescription = "")
                    Text(text = challenge.totalCompleted.toString())
                    Text(modifier = Modifier.padding(horizontal = 5.dp), text = "of")
                    Text(text = challenge.totalAttempts.toString())
                    Icon(
                        modifier = Modifier.padding(start = 20.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = ""
                    )
                    Text(text = challenge.createdBy.username)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Filled.Tag, contentDescription = "")
                    Text(text = challenge.tags.joinToString(", "))
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Description", style = MaterialTheme.typography.titleMedium)
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = challenge.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun Level(
    level: String
) {
    Surface(
        modifier = Modifier.padding(end = 0.dp, bottom = 0.dp),
        elevation = 8.dp,
        shape = CutCornerShape(13.dp),
        border = BorderStroke(
            width = 2.dp,
            color = Color.Cyan
        )
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 5.dp),
            text = level
        )
    }


}

@Preview(showBackground = false)
@Composable
private fun PreviewLevel() {
    Level("4 kyu")
}

@Preview(showBackground = true)
@Composable
private fun ChallengesDetailsScreenPreview() {
    ChallengesDetailsScreen(
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
}