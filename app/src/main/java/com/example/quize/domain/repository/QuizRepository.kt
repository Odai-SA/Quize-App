package com.example.quize.domain.repository

import com.example.quize.domain.model.Question

interface QuizRepository {
    suspend fun getQuestions(amount: Int, category: Int, difficulty: String): List<Question>
    suspend fun getCategories(): List<Pair<Int, String>>
}