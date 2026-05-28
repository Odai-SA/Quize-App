package com.example.quize.presentation.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quize.R
import com.example.quize.presentation.navigation.Screen
import com.example.quize.presentation.viewmodel.QuizViewModel
import com.example.quize.ui.theme.*

@Composable
fun QuizScreen(
    navController: NavController,
    categoryId: Int,
    difficulty: String,
    amount: Int
) {
    val viewModel: QuizViewModel = viewModel()

    LaunchedEffect(categoryId, difficulty, amount) {
        viewModel.reset()
        viewModel.loadQuestions(amount, categoryId, difficulty)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            viewModel.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DarkPrimary)
                }
            }

            viewModel.error.isNotEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Error: ${viewModel.error}",
                        color = DarkError,
                        fontSize = 16.sp
                    )
                }
            }

            viewModel.questions.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No questions found",
                        color = DarkOnSurfaceVariant,
                        fontSize = 16.sp
                    )
                }
            }

            else -> {
                val currentQuestion = viewModel.questions[viewModel.currentIndex]
                val progress = (viewModel.currentIndex + 1).toFloat() / viewModel.questions.size

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = DarkPrimary,
                    trackColor = DarkOutline
                )

                Text(
                    text = stringResource(
                        id = R.string.quiz_question_counter,
                        viewModel.currentIndex + 1,
                        viewModel.questions.size
                    ),
                    fontSize = 14.sp,
                    color = DarkOnSurfaceVariant,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Text(
                    text = currentQuestion.text,
                    fontSize = 20.sp,
                    color = DarkOnSurface,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                currentQuestion.answers.forEachIndexed { index, answer ->
                    val isSelected = viewModel.selectedAnswer == index
                    val isCorrect = answer == currentQuestion.correctAnswer

                    val cardColor = when {
                        !viewModel.answered -> DarkSurface
                        isCorrect -> DarkPrimaryContainer
                        isSelected -> DarkError.copy(alpha = 0.3f)
                        else -> DarkSurface
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable(enabled = !viewModel.answered) {
                                viewModel.selectAnswer(index)
                            },
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = answer,
                            modifier = Modifier.padding(16.dp),
                            color = DarkOnSurface,
                            fontSize = 16.sp
                        )
                    }
                }

                if (viewModel.answered) {
                    androidx.compose.material3.Button(
                        onClick = {
                            val hasNext = viewModel.nextQuestion()
                            if (!hasNext) {
                                navController.navigate(
                                    Screen.Score.createRoute(viewModel.score, viewModel.questions.size)
                                ) {
                                    popUpTo(Screen.Home.route)
                                }
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
                            text = if (viewModel.currentIndex < viewModel.questions.lastIndex)
                                stringResource(id = R.string.quiz_button_next)
                            else
                                "Finish (${viewModel.score}/${viewModel.questions.size})",
                            color = DarkOnPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}