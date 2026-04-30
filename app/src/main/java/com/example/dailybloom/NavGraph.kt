package com.example.dailybloom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String, val navIndex: Int) {
    object LoginScreen: Screen("login", -1)
    object BloomAIChatScreen: Screen("r0", 0)
    object BloomCalendarScreen: Screen("r1", 1)
    object BloomMainScreen: Screen("r2", 2)
    object BloomChecklistScreen: Screen("r3", 3)
    object BloomReportScreen: Screen("r4", 4)
}

@Composable
fun AppNavigation(userViewModel: UserViewModel) {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) { LoginScreen(navController, userViewModel) }
        composable(Screen.BloomAIChatScreen.route) { AIChatScreen(navController, userViewModel) }
        composable(Screen.BloomCalendarScreen.route) { CalendarScreen(navController, userViewModel)}
        composable(Screen.BloomMainScreen.route) { MainScreen(navController, userViewModel) }
        composable(Screen.BloomChecklistScreen.route) { ChecklistScreen(navController, userViewModel) }
        composable(Screen.BloomReportScreen.route) { ReportScreen(navController, userViewModel) }
    }
}
