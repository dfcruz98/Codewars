package com.cocus.codewars.data.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.cocus.codewars.data.local.CodewarsDatabase
import com.cocus.codewars.data.local.entities.CompletedChallengeEntity
import com.cocus.codewars.data.remote.TestCodewarsApi
import com.cocus.codewars.data.remote.TestCodewarsApiException
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class CompletedChallengesMediatorTest {

    private lateinit var testCodewarsApi: TestCodewarsApi
    private lateinit var testCodewarsApiException: TestCodewarsApiException
    private lateinit var database: CodewarsDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, CodewarsDatabase::class.java
        ).build()

        testCodewarsApi = TestCodewarsApi()
        testCodewarsApiException = TestCodewarsApiException()
    }


    @Test
    fun `refresh load returns success result when more data is present`() = runTest {
        val pagingState = PagingState<Int, CompletedChallengeEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val completedChallengesMediator = CompletedChallengesMediator(
            user = "mike",
            database = database,
            api = testCodewarsApi,
        )

        val result = completedChallengesMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
    }

    @Test
    fun `refresh load success and end of pagination when no more data`() = runTest {
        val pagingState = PagingState<Int, CompletedChallengeEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val completedChallengesMediator = CompletedChallengesMediator(
            user = "mike",
            database = database,
            api = testCodewarsApi,
        )

        val result = completedChallengesMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
    }

    @Test
    fun `refresh load returns error result when error occurs`() = runTest {
        val pagingState = PagingState<Int, CompletedChallengeEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val completedChallengesMediator = CompletedChallengesMediator(
            user = "mike",
            database = database,
            api = testCodewarsApiException,
        )

        val result = completedChallengesMediator.load(
            loadType = LoadType.REFRESH,
            state = pagingState
        )

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
    }
}