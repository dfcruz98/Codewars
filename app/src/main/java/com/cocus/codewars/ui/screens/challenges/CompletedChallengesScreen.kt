package com.cocus.codewars.ui.screens.challenges

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cocus.codewars.domain.models.CompletedChallenge
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun CompletedChallengesRoute(
    onClick: (CompletedChallenge) -> Unit,
    viewModel: CompletedChallengesViewModel = hiltViewModel()
) {
    val completedChallenges = viewModel.completedChallenges.collectAsLazyPagingItems()
    CompletedChallengesScreen(completedChallenges, onClick)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CompletedChallengesScreen(
    challenges: LazyPagingItems<CompletedChallenge>,
    onClick: (CompletedChallenge) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = challenges.loadState) {
        if (challenges.loadState.refresh is LoadState.Error) {
            Toast.makeText(context, "Error loading challenges", Toast.LENGTH_SHORT).show()
        }
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        itemCount += 5
        refreshing = false
    }

    val launchLazyListState = rememberLazyListState()
    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

        if (challenges.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface),
                state = launchLazyListState,
            ) {
                items(count = challenges.itemCount) { index ->
                    challenges[index]?.let { challenge ->
                        CompletedChallengeItem(challenge = challenge, onClick = onClick)
                    }
                }

                if (challenges.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CompletedChallengeItem(
    challenge: CompletedChallenge,
    modifier: Modifier = Modifier,
    onClick: (CompletedChallenge) -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable(
                onClick = { onClick(challenge) },
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = challenge.name
            )

            val timestampInstant = Instant.parse(challenge.completedAt)
            val articlePublishedZonedTime =
                ZonedDateTime.ofInstant(timestampInstant, ZoneId.systemDefault())
            val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            val date = articlePublishedZonedTime.format(dateFormatter)

            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = date
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                challenge.completedLanguages.forEach {
                    LanguageChip(it)
                }
            }
        }
    }
}


@Composable
fun LanguageChip(language: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(top = 5.dp)) {
        Surface(
            modifier = Modifier.padding(horizontal = 5.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(modifier = Modifier.padding(7.dp), text = language)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompletedChallengesScreenPreview() {
    val challenges = listOf(
        CompletedChallenge(
            id = "514b92a657cdc65150000006",
            name = "Multiples of 3 and 5",
            slug = "multiples-of-3-and-5",
            completedAt = "2017-04-06T16:32:09Z",
            completedLanguages = listOf("javascript")
        ),
        CompletedChallenge(
            id = "514b92a657cdc65150000007",
            name = "Multiples of 3 and 5",
            slug = "multiples-of-3-and-5",
            completedAt = "2017-04-06T16:32:09Z",
            completedLanguages = listOf("Javascript", "Kotlin", "C", "Rust")
        )
    )

    //CompletedChallengesScreen(PagingData.from(challenges))
}
