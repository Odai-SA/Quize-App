package com.example.quize.presentation.screens.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quize.presentation.navigation.Screen
import com.example.quize.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun ScoreScreen(
    navController: NavController,
    score: Int,
    total: Int
) {
    val wrong = total - score
    val accuracy = if (total > 0) ((score.toFloat() / total) * 100).roundToInt() else 0

    val emoji = when {
        accuracy == 100 -> "🏆"
        accuracy >= 80  -> "🎯"
        accuracy >= 50  -> "👍"
        else            -> "📚"
    }

    val message = when {
        accuracy == 100 -> "Perfect Score!"
        accuracy >= 80  -> "Great Job!"
        accuracy >= 50  -> "Good Effort!"
        else            -> "Keep Practicing!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Emoji circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(listOf(GradientStart, GradientEnd))
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 52.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = message,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkOnSurface
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Stats card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DarkSurface)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatRow(label = "✅ Correct",   value = "$score",     color = GradientStart)
            StatRow(label = "❌ Wrong",     value = "$wrong",     color = DarkError)
            StatRow(label = "📊 Accuracy",  value = "$accuracy%", color = GradientEnd)
            StatRow(label = "📝 Total",     value = "$total",     color = DarkOnSurfaceVariant)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Home button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Surface(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                color = androidx.compose.ui.graphics.Color.Transparent,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🏠  Back to Home",
                        color = androidx.compose.ui.graphics.Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 16.sp, color = DarkOnSurfaceVariant)
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
    }
}