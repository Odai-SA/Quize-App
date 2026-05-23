package com.example.quize.presentation.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quize.R
import com.example.quize.presentation.navigation.Screen
import com.example.quize.ui.theme.*

data class Question(
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)

@Composable
fun QuizScreen(navController: NavController) {
    val questions = remember {
        listOf(
            Question("What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
            Question("Which planet is known as the Red Planet?", listOf("Venus", "Mars", "Jupiter", "Saturn"), 1),
            Question("What is 2 + 2?", listOf("3", "4", "5", "6"), 1),
            Question("Who wrote 'Romeo and Juliet'?", listOf("Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"), 1),
            Question("What is the largest ocean?", listOf("Atlantic", "Indian", "Arctic", "Pacific"), 3)
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableIntStateOf(-1) }
    var answered by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentIndex]
    val progress = (currentIndex + 1).toFloat() / questions.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = DarkPrimary,
            trackColor = DarkOutline
        )

        Text(
            text = stringResource(id = R.string.quiz_question_counter, currentIndex + 1, questions.size),
            style = MaterialTheme.typography.titleLarge,
            color = DarkOnSurface,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.quiz_score_label, score),
            style = MaterialTheme.typography.bodyLarge,
            color = DarkSecondary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = DarkSurface
            )
        ) {
            Text(
                text = currentQuestion.text,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkOnSurface,
                modifier = Modifier.padding(20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            currentQuestion.answers.forEachIndexed { index, answer ->
                val isCorrect = index == currentQuestion.correctIndex
                val isSelected = index == selectedAnswer

                val containerColor = when {
                    answered && isCorrect -> DarkPrimary
                    answered && isSelected -> DarkError
                    else -> DarkSurfaceVariant
                }

                val textColor = when {
                    answered && isCorrect -> DarkOnPrimary
                    answered && isSelected -> DarkOnError
                    else -> DarkOnSurfaceVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !answered) {
                            selectedAnswer = index
                            answered = true
                            if (index == currentQuestion.correctIndex) score++
                        },
                    colors = CardDefaults.cardColors(containerColor = containerColor)
                ) {
                    Text(
                        text = answer,
                        color = textColor,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        if (answered) {
            androidx.compose.material3.Button(
                onClick = {
                    if (currentIndex < questions.lastIndex) {
                        currentIndex++
                        selectedAnswer = -1
                        answered = false
                    } else {
                        navController.navigate(Screen.Score.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = DarkPrimary
                )
            ) {
                Text(
                    text = if (currentIndex < questions.lastIndex)
                        stringResource(id = R.string.quiz_button_next)
                    else
                        stringResource(id = R.string.quiz_button_finish),
                    color = DarkOnPrimary
                )
            }
        }
    }
}