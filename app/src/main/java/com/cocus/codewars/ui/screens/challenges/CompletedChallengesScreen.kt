package com.cocus.codewars.ui.screens.challenges

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cocus.codewars.R
import com.cocus.codewars.domain.models.CompletedChallenge
import com.cocus.codewars.ui.components.CodewarsChip
import kotlinx.coroutines.launch

@Composable
fun CompletedChallengesRoute(
    onClick: (CompletedChallenge) -> Unit,
    viewModel: CompletedChallengesViewModel = hiltViewModel()
) {
    val completedChallenges = viewModel.completedChallenges.collectAsLazyPagingItems()
    CompletedChallengesScreen(completedChallenges, onClick)
}

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
    val launchLazyListState = rememberLazyListState()

    val showUpButton by remember {
        derivedStateOf { launchLazyListState.firstVisibleItemIndex > 0 }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("CompletedChallengesBox")
    ) {
        if (challenges.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag("CompletedChallengesLoading")
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("CompletedChallengesList"),
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

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomEnd),
                visible = showUpButton
            ) {
                ScrollUpButton(
                    modifier = Modifier
                        .testTag("ScrollUpButton")
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
            )
            .testTag("CompletedChallengesItem"),
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