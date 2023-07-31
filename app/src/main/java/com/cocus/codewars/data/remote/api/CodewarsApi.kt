package com.cocus.codewars.data.remote.api

import com.cocus.codewars.data.remote.dto.ChallengeDto
import com.cocus.codewars.data.remote.dto.CompletedChallengesPageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsApi {

    @GET("users/{user}/code-challenges/completed")
    suspend fun getCompletedChallenges(
        @Path("user") user: String,
        @Query("page") page: Int,
    ): Response<CompletedChallengesPageDto>

    @GET("code-challenges/{slug}")
    suspend fun getChallenge(
        @Path("slug") name: String,
    ): Response<ChallengeDto>
}