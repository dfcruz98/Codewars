package com.cocus.codewars.ui.screens.challenges

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompletedChallengesViewModelTest {

    private lateinit var viewModel: CompletedChallengesViewModel

    @Before
    fun setup() {
        viewModel = CompletedChallengesViewModel()
    }

    @Test
    fun test_items_contain_one_to_ten() = runTest {
        // Get the Flow of PagingData from the ViewModel under test
        val items = viewModel.completedChallenges.first()

        val itemsSnapshot: List<String> = items.asSnapshot {
            // Scroll to the 50th item in the list. This will also suspend till
            // the prefetch requirement is met if there's one.
            // It also suspends until all loading is complete.
            scrollTo(index = 50)
        }

        // With the asSnapshot complete, you can now verify that the snapshot
        // has the expected values
        assertEquals(
            expected = (0..50).map(Int::toString),
            actual = itemsSnapshot
        )
    }


}