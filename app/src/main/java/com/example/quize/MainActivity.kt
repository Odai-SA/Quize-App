package com.example.quize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quize.presentation.navigation.Screen
import com.example.quize.presentation.screens.home.HomeScreen
import com.example.quize.presentation.screens.login.LoginScreen
import com.example.quize.presentation.screens.onboarding.OnboardingScreen
import com.example.quize.presentation.screens.quiz.QuizScreen
import com.example.quize.presentation.screens.quizsetup.QuizSetupScreen
import com.example.quize.presentation.screens.register.RegisterScreen
import com.example.quize.presentation.screens.score.ScoreScreen
import com.example.quize.presentation.screens.splash.SplashScreen
import com.example.quize.ui.theme.QuizeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                QuizeTheme {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(navController = navController)
                        }

                        animatedComposable(Screen.Onboarding.route) {
                            OnboardingScreen(navController = navController)
                        }

                        animatedComposable(Screen.Login.route) {
                            LoginScreen(navController = navController)
                        }

                        animatedComposable(Screen.Register.route) {
                            RegisterScreen(navController = navController)
                        }

                        animatedComposable(Screen.Home.route) {
                            HomeScreen(navController = navController)
                        }

                        animatedComposable(Screen.QuizSetup.route) { backStackEntry ->
                            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 9
                            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "General Knowledge"
                            QuizSetupScreen(
                                navController = navController,
                                categoryId = categoryId,
                                categoryName = categoryName
                            )
                        }

                        animatedComposable(Screen.Quiz.route) { backStackEntry ->
                            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 9
                            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "easy"
                            val amount = backStackEntry.arguments?.getString("amount")?.toIntOrNull() ?: 10
                            QuizScreen(
                                navController = navController,
                                categoryId = categoryId,
                                difficulty = difficulty,
                                amount = amount
                            )
                        }
                        animatedComposable(Screen.Score.route) { backStackEntry ->
                            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
                            val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 0
                            ScoreScreen(
                                navController = navController,
                                score = score,
                                total = total
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun NavGraphBuilder.animatedComposable(
    route: String,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeIn(tween(400))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeOut(tween(400))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeIn(tween(400))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeOut(tween(400))
        }
    ) { backStackEntry ->
        content(backStackEntry)
    }
}