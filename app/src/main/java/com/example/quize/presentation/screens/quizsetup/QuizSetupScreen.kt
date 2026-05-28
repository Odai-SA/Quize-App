package com.example.quize.presentation.screens.quizsetup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quize.R
import com.example.quize.presentation.navigation.Screen
import com.example.quize.ui.theme.*

@Composable
fun QuizSetupScreen(
    navController: NavController,
    categoryId: Int = 9,
    categoryName: String = "General Knowledge"
) {
    var difficulty by remember { mutableStateOf("easy") }
    var questionCount by remember { mutableIntStateOf(10) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = categoryName,
            fontSize = 24.sp,
            color = DarkPrimary,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.setup_title),
            fontSize = 28.sp,
            color = DarkOnSurface,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = stringResource(id = R.string.setup_difficulty_label),
            fontSize = 18.sp,
            color = DarkOnSurfaceVariant,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "easy" to R.string.setup_difficulty_easy,
                "medium" to R.string.setup_difficulty_medium,
                "hard" to R.string.setup_difficulty_hard
            ).forEach { (key, labelRes) ->
                val isSelected = difficulty == key
                OutlinedButton(
                    onClick = { difficulty = key },
                    modifier = Modifier.weight(1f),
                    colors = if (isSelected)
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = DarkPrimaryContainer
                        )
                    else
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = DarkSurface
                        ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(id = labelRes),
                        color = if (isSelected) DarkOnPrimaryContainer else DarkOnSurfaceVariant
                    )
                }
            }
        }

        Text(
            text = stringResource(id = R.string.setup_questions_label),
            fontSize = 18.sp,
            color = DarkOnSurfaceVariant,
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )

        Text(
            text = "$questionCount",
            fontSize = 32.sp,
            color = DarkPrimary
        )

        Slider(
            value = questionCount.toFloat(),
            onValueChange = { questionCount = it.toInt() },
            valueRange = 1f..20f,
            steps = 18,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = DarkPrimary,
                activeTrackColor = DarkPrimary,
                inactiveTrackColor = DarkOutline
            )
        )

        androidx.compose.material3.Surface(
            onClick = {
                navController.navigate(
                    Screen.Quiz.createRoute(categoryId, difficulty, questionCount)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(26.dp)),
            color = androidx.compose.ui.graphics.Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(GradientStart, GradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.setup_button_start),
                    color = androidx.compose.ui.graphics.Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}