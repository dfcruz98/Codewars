package com.cocus.codewars.ui.screens.challenges

import androidx.lifecycle.ViewModel
import com.cocus.codewars.domain.repositories.ChallengesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val USER = "g964"

@HiltViewModel
class CompletedChallengesViewModel @Inject constructor(
    repository: ChallengesRepository,
) : ViewModel() {

    val completedChallenges = repository.getCompletedChallenges(USER)
}