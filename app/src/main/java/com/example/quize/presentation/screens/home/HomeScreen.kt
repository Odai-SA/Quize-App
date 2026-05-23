package com.example.quize.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quize.R
import com.example.quize.presentation.navigation.Screen

data class Category(val id: Int, val name: String, val emoji: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val categories = listOf(
        Category(9, "General Knowledge", "🌍"),
        Category(10, "Entertainment: Books", "📚"),
        Category(11, "Entertainment: Film", "🎬"),
        Category(12, "Entertainment: Music", "🎵"),
        Category(13, "Entertainment: Musicals & Theatres", "🎭"),
        Category(14, "Entertainment: Television", "📺"),
        Category(15, "Entertainment: Video Games", "🎮"),
        Category(16, "Entertainment: Board Games", "🎲"),
        Category(17, "Science & Nature", "🔬"),
        Category(18, "Science: Computers", "💻"),
        Category(19, "Science: Mathematics", "📐"),
        Category(20, "Mythology", "🏛️"),
        Category(21, "Sports", "⚽"),
        Category(22, "Geography", "🗺️"),
        Category(23, "History", "📜"),
        Category(24, "Politics", "🏛️"),
        Category(25, "Art", "🎨"),
        Category(26, "Celebrities", "⭐"),
        Category(27, "Animals", "🐾"),
        Category(28, "Vehicles", "🚗"),
        Category(29, "Entertainment: Comics", "💥"),
        Category(30, "Science: Gadgets", "📱"),
        Category(31, "Entertainment: Japanese Anime & Manga", "⛩️"),
        Category(32, "Entertainment: Cartoon & Animations", "🦁")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.home_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    TextButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Text(text = "⚙️", fontSize = MaterialTheme.typography.titleLarge.fontSize)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_welcome),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(categories) { category ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(Screen.QuizSetup.route) },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = category.emoji,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}