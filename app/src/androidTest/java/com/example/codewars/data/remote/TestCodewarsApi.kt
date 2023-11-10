package com.example.codewars.data.remote

import com.example.codewars.data.remote.api.CodewarsApi
import com.example.codewars.data.remote.dto.ChallengeDto
import com.example.codewars.data.remote.dto.CompletedChallengeDto
import com.example.codewars.data.remote.dto.CompletedChallengesPageDto
import retrofit2.Response

class TestCodewarsApi : CodewarsApi {

    override suspend fun getCompletedChallenges(
        user: String,
        page: Int
    ): Response<CompletedChallengesPageDto> {
        return Response.success(
            CompletedChallengesPageDto(
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
        )
    }

    override suspend fun getChallenge(name: String): Response<ChallengeDto> {
        TODO("Not yet implemented")
    }
}