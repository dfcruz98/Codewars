package com.cocus.codewars.ui.screens.challenges

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cocus.codewars.R
import com.cocus.codewars.domain.models.CompletedChallenge
import com.cocus.codewars.ui.components.CodewarsChip
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            Toast.makeText(
                context,
                context.getString(R.string.error_loading_challenges),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        refreshing = false
    }

    val launchLazyListState = rememberLazyListState()
    val state = rememberPullRefreshState(refreshing, ::refresh)

    val showUpButton by remember {
        derivedStateOf { launchLazyListState.firstVisibleItemIndex > 0 }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter).height(1.dp))

        if (challenges.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
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

            if (showUpButton) {
                ScrollUpButton(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    refreshScope.launch {
                        launchLazyListState.scrollToItem(0)
                    }
                }
            }

        }
    }
}

@Composable
private fun ScrollUpButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier
            .padding(16.dp)
            .size(50.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowUpward,
            contentDescription = stringResource(R.string.go_to_top)
        )
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
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = challenge.completedAt
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                challenge.completedLanguages.forEach {
                    CodewarsChip(it)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CompletedChallengesScreenPreview() {
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
