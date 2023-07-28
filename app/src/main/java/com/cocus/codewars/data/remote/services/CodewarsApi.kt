package com.cocus.codewars.data.remote.services

import com.cocus.codewars.data.remote.dto.ChallengeDto
import com.cocus.codewars.data.remote.dto.CompletedChallengesPageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsApi {

    @GET("users/{user}/code-challenges/completed")
    suspend fun getCompletedChallenges(
        @Path("user") user: String,
        @Query("page") page: Int,
    ): CompletedChallengesPageDto

    @GET("code-challenges/valid-braces")
    fun getChallenge(
        @Path("challenge") challenge: String,
    ): ChallengeDto
}