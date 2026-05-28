package com.example.quize.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object QuizSetup : Screen("quiz_setup/{categoryId}/{categoryName}") {
        fun createRoute(categoryId: Int, categoryName: String) = "quiz_setup/$categoryId/$categoryName"
    }
    data object Quiz : Screen("quiz/{categoryId}/{difficulty}/{amount}") {
        fun createRoute(categoryId: Int, difficulty: String, amount: Int) = "quiz/$categoryId/$difficulty/$amount"
    }
    data object Score : Screen("score/{score}/{total}") {
        fun createRoute(score: Int, total: Int) = "score/$score/$total"
    }
}