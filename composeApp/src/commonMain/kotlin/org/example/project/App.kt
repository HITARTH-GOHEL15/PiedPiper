package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.example.project.design.MockMateTheme
import org.example.project.nav.MockMateNavigation
import org.example.project.ui.FeedbackScreen
import org.example.project.ui.HistoryScreen
import org.example.project.ui.home_dashboard.HomeScreen
import org.example.project.ui.onBoarding.OnboardingScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MockMateTheme {
      Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
      ) {
     val navController = rememberNavController()
          MockMateNavigation(navController = navController)
      }
  }
}