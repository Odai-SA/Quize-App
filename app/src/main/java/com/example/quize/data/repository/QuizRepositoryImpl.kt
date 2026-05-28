package com.example.quize.data.repository

import com.example.quize.data.remote.api.QuizApiService
import com.example.quize.domain.model.Question
import com.example.quize.domain.repository.QuizRepository
import toDomain

class QuizRepositoryImpl(
    private val api: QuizApiService
) : QuizRepository {

    override suspend fun getQuestions(amount: Int, category: Int, difficulty: String): List<Question> {
        val response = api.getQuestions(amount, category, difficulty)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getCategories(): List<Pair<Int, String>> {
        val response = api.getCategories()
        return response.trivia_categories.map { it.id to it.name }
    }
}