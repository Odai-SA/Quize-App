package com.example.quize.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quize.data.di.AppModule
import com.example.quize.domain.model.Question
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val repository = AppModule.quizRepository

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var currentIndex by mutableIntStateOf(0)
        private set

    var score by mutableIntStateOf(0)
        private set

    var selectedAnswer by mutableIntStateOf(-1)
        private set

    var answered by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    fun loadQuestions(amount: Int, category: Int, difficulty: String) {
        viewModelScope.launch {
            isLoading = true
            error = ""
            try {
                questions = repository.getQuestions(amount, category, difficulty)
            } catch (e: Exception) {
                error = e.message ?: "Network error"
            }
            isLoading = false
        }
    }

    fun selectAnswer(index: Int) {
        if (answered) return
        selectedAnswer = index
        answered = true
        if (questions[currentIndex].answers[index] == questions[currentIndex].correctAnswer) {
            score++
        }
    }

    fun nextQuestion(): Boolean {
        return if (currentIndex < questions.lastIndex) {
            currentIndex++
            selectedAnswer = -1
            answered = false
            true
        } else {
            false
        }
    }

    fun reset() {
        questions = emptyList()
        currentIndex = 0
        score = 0
        selectedAnswer = -1
        answered = false
        error = ""
    }
}