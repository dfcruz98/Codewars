package com.cocus.codewars.ui.screens.challengedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cocus.codewars.domain.models.Challenge
import com.cocus.codewars.domain.repositories.ChallengesRepository
import com.cocus.codewars.utils.Result
import com.cocus.codewars.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ChallengesRepository,
) : ViewModel() {

    private val challengeSlug: String = checkNotNull(savedStateHandle[challengeDetailsId])

    val challenge = getChallenge(challengeSlug)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ChallengeUiState.Loading,
        )

    private fun getChallenge(slug: String): Flow<ChallengeUiState> {
        return repository.getChallenge(slug)
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Error -> ChallengeUiState.Error
                    Result.Loading -> ChallengeUiState.Loading
                    is Result.Success -> {
                        ChallengeUiState.Success(result.data)
                    }
                }
            }
    }

}

sealed interface ChallengeUiState {
    data class Success(val challenge: Challenge?) : ChallengeUiState
    object Error : ChallengeUiState
    object Loading : ChallengeUiState
}