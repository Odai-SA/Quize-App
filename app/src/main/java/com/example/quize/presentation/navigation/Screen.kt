package com.example.quize.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object QuizSetup : Screen("quiz_setup")
    data object Quiz : Screen("quiz")
    data object Score : Screen("score")
    data object Settings : Screen("settings")
}