package com.example.dailybloom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

private val PrimaryGreen  = Color(0xFF4CAF6E)
private val NavUnselected = Color(0xFFADBFB0)

fun navIcon(screen: Screen) = when (screen) {
    Screen.BloomAIChatScreen -> Icons.AutoMirrored.Outlined.Chat
    Screen.BloomCalendarScreen -> Icons.Outlined.CalendarMonth
    Screen.BloomChecklistScreen -> Icons.AutoMirrored.Outlined.FormatListBulleted
    Screen.BloomReportScreen -> Icons.Outlined.BarChart
    else -> Icons.Filled.Eco
}

fun navLabel(screen: Screen) = when (screen) {
    Screen.BloomAIChatScreen -> "AI 채팅"
    Screen.BloomCalendarScreen -> "캘린더"
    Screen.BloomChecklistScreen -> "리스트"
    Screen.BloomReportScreen -> "리포트"
    else -> "현황"
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val screens = listOf(
        Screen.BloomAIChatScreen,
        Screen.BloomCalendarScreen,
        Screen.BloomMainScreen,
        Screen.BloomChecklistScreen,
        Screen.BloomReportScreen
    )

    Surface(
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                if (screen == Screen.BloomMainScreen) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(PrimaryGreen)
                            .clickable { navController.navigate(screen.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = navIcon(screen),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(42.dp)
                        )
                    }
                } else {
                    val isSelected = screen.route == currentRoute
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { navController.navigate(screen.route) }
                    ) {
                        Icon(
                            imageVector = navIcon(screen),
                            contentDescription = "",
                            tint = if (isSelected) PrimaryGreen else NavUnselected,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = navLabel(screen),
                            fontSize = 10.sp,
                            color = if (isSelected) PrimaryGreen else NavUnselected
                        )
                    }
                }
            }
        }
    }
}
