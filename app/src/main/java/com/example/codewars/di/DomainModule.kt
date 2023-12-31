package com.example.codewars.di

import com.example.codewars.data.local.CodewarsDatabase
import com.example.codewars.data.remote.api.CodewarsApi
import com.example.codewars.data.repositories.ChallengesRepositoryImpl
import com.example.codewars.data.repositories.ChallengesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideChallengesRepository(
        api: CodewarsApi,
        database: CodewarsDatabase,
    ): ChallengesRepository {
        return ChallengesRepositoryImpl(api, database)
    }

}