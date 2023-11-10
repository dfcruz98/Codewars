package com.example.codewars.di

import android.content.Context
import androidx.room.Room
import com.example.codewars.data.local.CodewarsDatabase
import com.example.codewars.data.local.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CodewarsDatabase {
        return Room.databaseBuilder(
            appContext,
            CodewarsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}