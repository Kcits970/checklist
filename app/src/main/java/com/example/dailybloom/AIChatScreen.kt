package com.example.dailybloom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
private val BotBubble       = Color(0xFFFFFFFF)

@Composable
fun AIChatScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var messageText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = BackgroundColor,
        bottomBar = {
            Column {
                Surface(
                    color = CardColor,
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = messageText,
                            onValueChange = { messageText = it },
                            placeholder = {
                                Text("메시지를 입력하세요...", color = TextSecondary, fontSize = 14.sp)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFE0EDE4),
                                focusedBorderColor = PrimaryGreen,
                                unfocusedContainerColor = Color(0xFFF8FCF8),
                                focusedContainerColor = Color(0xFFF8FCF8),
                            ),
                            singleLine = true
                        )
                        Spacer(Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(PrimaryGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "전송",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(28.dp))

            // Header
            Text(
                text = "AI 상담",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "언제든지 편하게 이야기 나눠요",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(Modifier.height(20.dp))

            // Bot message bubble
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = BotBubble),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Bot avatar
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(PrimaryGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.SmartToy,
                            contentDescription = "AI",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "안녕하세요! 오늘 하루는 어떠셨나요? 편하게 이야기 나눠요 😊",
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 20.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "오후 2:30",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "빠른 작업",
                fontSize = 13.sp,
                color = TextSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            val quickActions = listOf(
                QuickAction(Icons.Outlined.TrackChanges, "목표 수정하기"),
                QuickAction(Icons.Outlined.TrendingUp,   "난이도 조정"),
                QuickAction(Icons.Outlined.Add,          "습관 추가"),
                QuickAction(Icons.Outlined.Remove,       "습관 줄이기"),
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                quickActions.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        row.forEach { action ->
                            QuickActionCard(
                                action = action,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

private data class QuickAction(val icon: ImageVector, val label: String)

@Composable
private fun QuickActionCard(action: QuickAction, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(LightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = action.icon,
                    contentDescription = action.label,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(22.dp)
                )
            }
            Text(
                text = action.label,
                fontSize = 13.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AIChatScreenPreview() {
    MaterialTheme { AIChatScreen(rememberNavController(), viewModel()) }
}
