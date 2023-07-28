package com.cocus.codewars.ui.screens.challengedetails

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.repositories.ChallengesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    private val repository: ChallengesRepository,
) : ViewModel() {

    val challenge = MutableStateFlow<Challenge?>(null)

    fun getChallenge(name: String) {
        viewModelScope.launch {
            repository.getChallenge(name)
                .catch { Log.d("ChallengeDetailsViewModel", "error") }
                .collect { challenge.value }
        }
    }
}