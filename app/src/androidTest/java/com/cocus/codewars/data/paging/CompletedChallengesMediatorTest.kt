package com.cocus.codewars.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.cocus.codewars.data.local.entities.CompletedChallengeEntity
import com.cocus.codewars.data.remote.TestCodewarsApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class CompletedChallengesMediatorTest {

    private lateinit var completedChallengesMediator: CompletedChallengesMediator
    private val testCodewarsApi = TestCodewarsApi()

    @Before
    fun setup() {
        completedChallengesMediator = CompletedChallengesMediator(
            user = "mike",
            database = ,
            api = testCodewarsApi,
        )
    }


    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val pagingState = PagingState<Int, CompletedChallengeEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = completedChallengesMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        val pagingState = PagingState<Int, CompletedChallengeEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = completedChallengesMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        val pagingState = PagingState<Int, CompletedChallengeEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = completedChallengesMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
    }


}