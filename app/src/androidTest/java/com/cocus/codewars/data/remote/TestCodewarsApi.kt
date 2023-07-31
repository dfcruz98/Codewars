package com.cocus.codewars.data.remote

import com.cocus.codewars.data.remote.dto.ChallengeDto
import com.cocus.codewars.data.remote.dto.CompletedChallengeDto
import com.cocus.codewars.data.remote.dto.CompletedChallengesPageDto
import com.cocus.codewars.data.remote.services.CodewarsApi
import retrofit2.Response

class TestCodewarsApi : CodewarsApi {

    override suspend fun getCompletedChallenges(
        user: String,
        page: Int
    ): CompletedChallengesPageDto {
        return CompletedChallengesPageDto(
            totalPages = 10,
            totalItems = 100,
            data = listOf(
                CompletedChallengeDto(
                    id = "514b92a657cdc65150000006",
                    name = "Multiples of 3 and 5",
                    slug = "multiples-of-3-and-5",
                    completedAt = "2017-04-06T16:32:09Z",
                    completedLanguages = listOf("javascript")
                )
            )
        )
    }

    override suspend fun getChallenge(name: String): Response<ChallengeDto> {
        TODO("Not yet implemented")
    }
}