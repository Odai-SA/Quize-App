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
import com.example.quize.presentation.screens.settings.SettingsScreen
import com.example.quize.presentation.screens.splash.SplashScreen
import com.example.quize.ui.theme.QuizeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // FORCE LTR FOR ENTIRE APP — regardless of phone language
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

                        animatedComposable(Screen.QuizSetup.route) {
                            QuizSetupScreen(navController = navController)
                        }

                        animatedComposable(Screen.Quiz.route) {
                            QuizScreen(navController = navController)
                        }

                        animatedComposable(Screen.Score.route) {
                            ScoreScreen(navController = navController)
                        }

                        animatedComposable(Screen.Settings.route) {
                            SettingsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

private fun NavGraphBuilder.animatedComposable(
    route: String,
    content: @Composable () -> Unit
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
    ) {
        content()
    }
}