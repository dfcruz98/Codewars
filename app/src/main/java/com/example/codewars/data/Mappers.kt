package com.example.codewars.data

import com.example.codewars.data.local.entities.ApprovedByEntity
import com.example.codewars.data.local.entities.ChallengeEntity
import com.example.codewars.data.local.entities.CompletedChallengeEntity
import com.example.codewars.data.local.entities.CreatedByEntity
import com.example.codewars.data.local.entities.RankEntity
import com.example.codewars.data.remote.dto.ChallengeDto
import com.example.codewars.data.remote.dto.CompletedChallengeDto
import com.example.codewars.data.models.ApprovedBy
import com.example.codewars.data.models.Challenge
import com.example.codewars.data.models.CompletedChallenge
import com.example.codewars.data.models.CreatedBy
import com.example.codewars.data.models.Rank
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun CompletedChallengeDto.toCompletedChallengeEntity(user: String) = CompletedChallengeEntity(
    id = id,
    user = user,
    name = name,
    slug = slug,
    completedAt = completedAt,
    completedLanguages = completedLanguages,
)

fun CompletedChallengeEntity.toCompletedChallenge(): CompletedChallenge {
    val timestampInstant = Instant.parse(completedAt)
    val zone = ZonedDateTime.ofInstant(timestampInstant, ZoneId.systemDefault())
    val date = zone.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
    return CompletedChallenge(
        id = id,
        name = name,
        slug = slug,
        completedAt = date,
        completedLanguages = completedLanguages,
    )
}

fun ChallengeDto.toChallengeEntity() = ChallengeEntity(
    id = this.id,
    name = this.name,
    slug = this.slug,
    url = this.url,
    category = this.category,
    description = this.description,
    tags = this.tags,
    languages = this.languages,
    rank = RankEntity(
        id = this.rank.id,
        name = this.rank.name,
        color = this.rank.color,
    ),
    createdBy = CreatedByEntity(
        username = this.createdBy.username,
        url = this.createdBy.url,
    ),
    approvedBy = ApprovedByEntity(
        username = this.approvedBy.username,
        url = this.approvedBy.url,
    ),
    totalAttempts = this.totalAttempts,
    totalCompleted = this.totalCompleted,
    totalStars = this.totalStars,
    voteScore = this.voteScore,
    publishedAt = this.publishedAt,
    approvedAt = this.approvedAt
)

fun ChallengeEntity.toChallenge() = Challenge(
    id = this.id,
    name = this.name,
    slug = this.slug,
    url = this.url,
    category = this.category,
    description = this.description,
    tags = this.tags,
    languages = this.languages,
    rank = Rank(
        id = this.rank.id,
        name = this.rank.name,
        color = this.rank.color,
    ),
    createdBy = CreatedBy(
        username = this.createdBy.username,
        url = this.createdBy.url,
    ),
    approvedBy = ApprovedBy(
        username = this.approvedBy.username,
        url = this.approvedBy.url,
    ),
    totalAttempts = this.totalAttempts,
    totalCompleted = this.totalCompleted,
    totalStars = this.totalStars,
    voteScore = this.voteScore,
    publishedAt = this.publishedAt,
    approvedAt = this.approvedAt
)