package com.example.quize.domain.model

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String
)