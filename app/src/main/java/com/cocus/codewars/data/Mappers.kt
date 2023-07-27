package com.cocus.codewars.data

import com.cocus.codewars.data.local.entities.CompletedChallengeEntity
import com.cocus.codewars.data.remote.dto.CompletedChallengeDto
import com.cocus.codewars.domain.models.CompletedChallenge

fun CompletedChallengeDto.toCompletedChallengeEntity(user: String) = CompletedChallengeEntity(
    id = id,
    user = user,
    name = name,
    slug = slug,
    completedAt = completedAt,
    completedLanguages = completedLanguages,
)

fun CompletedChallengeEntity.toCompletedChallenge() = CompletedChallenge(
    id = id,
    name = name,
    slug = slug,
    completedAt = completedAt,
    completedLanguages = completedLanguages,
)