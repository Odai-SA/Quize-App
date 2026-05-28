package com.example.quize.data.di

import com.example.quize.data.remote.api.QuizApiService
import com.example.quize.data.repository.QuizRepositoryImpl
import com.example.quize.domain.repository.QuizRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private const val BASE_URL = "https://opentdb.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: QuizApiService = retrofit.create(QuizApiService::class.java)

    val quizRepository: QuizRepository = QuizRepositoryImpl(apiService)
}