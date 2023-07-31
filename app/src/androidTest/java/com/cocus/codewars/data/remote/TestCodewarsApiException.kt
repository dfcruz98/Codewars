package com.cocus.codewars.data.remote

import com.cocus.codewars.data.remote.dto.ChallengeDto
import com.cocus.codewars.data.remote.dto.CompletedChallengesPageDto
import com.cocus.codewars.data.remote.services.CodewarsApi
import retrofit2.Response
import java.io.IOException

class TestCodewarsApiException : CodewarsApi {

    override suspend fun getCompletedChallenges(
        user: String,
        page: Int
    ): CompletedChallengesPageDto {
        throw IOException()
    }

    override suspend fun getChallenge(name: String): Response<ChallengeDto> {
        throw IOException()
    }
}