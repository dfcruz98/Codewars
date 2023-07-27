package com.cocus.codewars.di

import com.cocus.codewars.data.remote.services.CodewarsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://www.codewars.com/api/v1/"

private const val HTTP_REQUEST_READ_TIMEOUT = 30L
private const val HTTP_REQUEST_WRITE_TIMEOUT = 30L
private const val HTTP_REQUEST_CONNECTION_TIMEOUT = 10L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val builder = OkHttpClient
            .Builder()
            .connectTimeout(HTTP_REQUEST_READ_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_REQUEST_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_REQUEST_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logging)

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideCodewarsApi(retrofit: Retrofit): CodewarsApi {
        return retrofit.create(CodewarsApi::class.java)
    }
}