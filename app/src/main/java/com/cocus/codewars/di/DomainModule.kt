package com.cocus.codewars.di

import com.cocus.codewars.data.local.CodewarsDatabase
import com.cocus.codewars.data.remote.services.CodewarsApi
import com.cocus.codewars.data.repositories.ChallengesRepositoryImpl
import com.cocus.codewars.domain.repositories.ChallengesRepository
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