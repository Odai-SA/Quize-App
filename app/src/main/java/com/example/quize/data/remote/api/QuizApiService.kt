package com.example.quize.data.remote.api

import com.example.quize.data.remote.dto.QuizResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String = "multiple"
    ): QuizResponseDto

    @GET("api_category.php")
    suspend fun getCategories(): CategoryResponseDto
}

data class CategoryResponseDto(
    val trivia_categories: List<CategoryDto>
)

data class CategoryDto(
    val id: Int,
    val name: String
)