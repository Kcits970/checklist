package com.example.dailybloom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun ChecklistScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val habits = listOf("물 마시기", "창문 열기", "1분 스트레칭", "짧은 산책")
    val checkedStates = remember { mutableStateListOf(false, false, false, false) }
    val completedCount = checkedStates.count { it }

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
            Spacer(Modifier.height(28.dp))

            Text(
                text = "오늘의 습관",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(Modifier.height(16.dp))

            // Progress header card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryGreen),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "오늘의 진행률",
                                fontSize = 12.sp,
                                color = Color(0xCCFFFFFF)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "$completedCount / ${habits.size}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        // Streak badge
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0x33FFFFFF))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "🔥", fontSize = 14.sp)
                            Text(
                                text = "7일 연속",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Progress bar
                    val progress = if (habits.isEmpty()) 0f else completedCount.toFloat() / habits.size
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0x55FFFFFF))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.White)
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Habit list
            habits.forEachIndexed { index, habit ->
                HabitItem(
                    label = habit,
                    isChecked = checkedStates[index],
                    onToggle = { checkedStates[index] = !checkedStates[index] }
                )
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun HabitItem(
    label: String,
    isChecked: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked) LightGreen else CardColor
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Custom radio / check circle
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isChecked) PrimaryGreen else Color.Transparent)
                    .border(
                        width = 2.dp,
                        color = if (isChecked) PrimaryGreen else Color(0xFFBDD8C4),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) {
                    Text(text = "✓", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = label,
                fontSize = 16.sp,
                color = if (isChecked) Color(0xFF3A7A50) else TextPrimary,
                fontWeight = if (isChecked) FontWeight.Medium else FontWeight.Normal,
                textDecoration = if (isChecked)
                    androidx.compose.ui.text.style.TextDecoration.LineThrough
                else null
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChecklistScreenPreview() {
    MaterialTheme { ChecklistScreen(rememberNavController(), viewModel()) }
}
