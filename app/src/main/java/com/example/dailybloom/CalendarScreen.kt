package com.example.dailybloom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

private val BackgroundColor = Color(0xFFF2F7F2)
private val CardColor       = Color(0xFFFFFFFF)
private val PrimaryGreen    = Color(0xFF4CAF6E)
private val LightGreen      = Color(0xFFDDEFE2)
private val TextPrimary     = Color(0xFF1A2E1A)
private val TextSecondary   = Color(0xFF7A9080)
private val SundayRed       = Color(0xFFE57373)
private val AchieveGreen    = Color(0xFF4CAF6E)

@Composable
fun CalendarScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val completedDays = setOf(2, 5, 8, 10, 12, 15, 18, 19, 20, 22, 24, 25, 26)
    val today = 27
    val startDayOfWeek = 3
    val totalDays = 30

    Scaffold(
        containerColor = BackgroundColor,
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.ChevronLeft, contentDescription = "이전 달", tint = TextSecondary)
                }
                Text(
                    text = "2026년 4월",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.ChevronRight, contentDescription = "다음 달", tint = TextSecondary)
                }
            }

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val dayLabels = listOf("일", "월", "화", "수", "목", "금", "토")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        dayLabels.forEachIndexed { index, label ->
                            Text(
                                text = label,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = when (index) {
                                    0 -> SundayRed
                                    else -> TextSecondary
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    val totalCells = startDayOfWeek + totalDays
                    val rows = (totalCells + 6) / 7

                    for (row in 0 until rows) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (col in 0 until 7) {
                                val cellIndex = row * 7 + col
                                val day = cellIndex - startDayOfWeek + 1
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (day in 1..totalDays) {
                                        CalendarDay(
                                            day = day,
                                            isToday = day == today,
                                            isCompleted = day in completedDays,
                                            isSunday = col == 0
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = LightGreen),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "이번 달 성과",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(text = "🎉", fontSize = 20.sp)
                    }
                    Spacer(Modifier.height(14.dp))
                    AchievementRow(label = "완료한 날", value = "18일")
                    Spacer(Modifier.height(10.dp))
                    AchievementRow(label = "연속 기록", value = "7일")
                    Spacer(Modifier.height(10.dp))
                    AchievementRow(label = "달성률", value = "60%", valueColor = AchieveGreen)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CalendarDay(
    day: Int,
    isToday: Boolean,
    isCompleted: Boolean,
    isSunday: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isToday -> PrimaryGreen
                        isCompleted -> LightGreen
                        else -> Color.Transparent
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.toString(),
                fontSize = 13.sp,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    isToday -> Color.White
                    isSunday -> SundayRed
                    else -> TextPrimary
                }
            )
        }

        if (isCompleted && !isToday) {
            Spacer(Modifier.height(2.dp))
            Text(text = "✓", fontSize = 9.sp, color = PrimaryGreen)
        } else if (isToday) {
            Spacer(Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen)
            )
        } else {
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
private fun AchievementRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF1A2E1A)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, color = TextSecondary)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = valueColor)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarScreenPreview() {
    MaterialTheme { CalendarScreen(rememberNavController(), viewModel()) }
}
