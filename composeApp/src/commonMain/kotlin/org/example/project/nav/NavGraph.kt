package org.example.project.nav

import LoginScreen
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import org.example.project.ui.FeedbackScreen
import org.example.project.ui.HistoryScreen
import org.example.project.ui.home_dashboard.HomeScreen
import org.example.project.ui.interview_interface.InterviewScreen
import org.example.project.ui.onBoarding.OnboardingScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Interview : Screen("interview")
    object Feedback : Screen("feedback")
    object History : Screen("history")
}

@Composable
fun MockMateNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
           OnboardingScreen(
               onGetStarted = {
                   navController.navigate(Screen.Login.route) {
                       popUpTo(Screen.Onboarding.route) { inclusive = true }
                   }
               }
           )
        }

        composable(Screen.Login.route) {
           LoginScreen(
               onLoginSuccess = {
                   navController.navigate(Screen.Home.route) {
                       popUpTo(Screen.Login.route) { inclusive = true }
                   }
               },
               onSignUpClick = {
                   // Handle sign up - could navigate to sign up screen
               }
           )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onStartInterview = {
                    navController.navigate(Screen.Interview.route)
                },
                onViewProgress = {
                    navController.navigate(Screen.History.route)
                },
                onViewTips = {

                },
                onNavigateToSettings = {
                }
            )
        }

        composable(Screen.Interview.route) {
            InterviewScreen(
                onInterviewComplete = { feedbackData ->
                    navController.navigate(Screen.Feedback.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            Screen.Feedback.route
        ) {
            FeedbackScreen(
                onBackToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onTryAgain = {
                    navController.navigate(Screen.Interview.route)
                },
                onSettingsClick = {}
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onViewFeedback = { interviewId ->
                    navController.navigate(Screen.Feedback.route)
                }
            )
        }
    }
}

// Shared ViewModel for Navigation State
class NavigationViewModel {
    private val _currentScreen = mutableStateOf(Screen.Onboarding.route)
    val currentScreen: State<String> = _currentScreen

    fun updateCurrentScreen(screen: String) {
        _currentScreen.value = screen
    }
}