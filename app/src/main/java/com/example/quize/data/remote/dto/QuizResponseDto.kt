package com.example.quize.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuizResponseDto(
    @SerializedName("response_code")
    val responseCode: Int,
    val results: List<QuestionDto>
)

data class QuestionDto(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)