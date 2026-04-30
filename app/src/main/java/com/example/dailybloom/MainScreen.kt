package com.example.dailybloom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState

private val BackgroundColor   = Color(0xFFF2F7F2)
private val CardColor         = Color(0xFFFFFFFF)
private val PrimaryGreen      = Color(0xFF4CAF6E)
private val LightGreen        = Color(0xFFDDEFE2)
private val AccentGreen       = Color(0xFF66BB6A)
private val TextPrimary       = Color(0xFF1A2E1A)
private val TextSecondary     = Color(0xFF7A9080)
private val ProgressTrack     = Color(0xFFE0EDE4)
private val TipBackground     = Color(0xFFFFFDE7)
private val TipBorder         = Color(0xFFFFF176)

@Composable
fun MainScreen(navController: NavHostController, userViewModel: UserViewModel) {
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
                text = "오늘도 함께해요, ${userViewModel.user.collectAsState().value.nickname}님! \uD83C\uDF31",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "작은 습관들이 모여 아름다운 나무가 되어요",
                fontSize = 14.sp,
                color = TextSecondary
            )
            Spacer(Modifier.height(20.dp))
            TreeGrowthCard(
                stage = 3,
                daysCompleted = 18,
                totalDays = 30
            )
            Spacer(Modifier.height(16.dp))
            StatsRow(
                todayHabit = "3/4",
                streak = "7일",
                badges = "5개"
            )
            Spacer(Modifier.height(16.dp))
            GrowthTipCard(
                tip = "매일 작은 습관을 완료할 때마다 새싹이 자라요. 다음 단계까지 12일 남았어요. 꾸준히 실천하면 아름다운 나무로 성장할 거예요!"
            )
        }
    }
}

@Composable
fun TreeGrowthCard(
    stage: Int,
    daysCompleted: Int,
    totalDays: Int
) {
    val progress = daysCompleted.toFloat() / totalDays.toFloat()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6BAE7A),
                        Color(0xFF4A8A5A),
                        Color(0xFF2D6B40)
                    )
                )
            )
    ) {
        // Decorative overlay gradient at bottom for text legibility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0x99000000))
                    )
                )
        )

        // Content overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Stage label
            Text(
                text = "성장 단계",
                fontSize = 12.sp,
                color = Color(0xCCFFFFFF),
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "단계 $stage",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            // Progress bar
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

            Spacer(Modifier.height(4.dp))

            // Progress text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${daysCompleted}일 완료",
                    fontSize = 11.sp,
                    color = Color(0xCCFFFFFF)
                )
                Text(
                    text = "${totalDays}일 중",
                    fontSize = 11.sp,
                    color = Color(0xCCFFFFFF)
                )
            }
        }

        // "성장 중" badge – top right
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0x33FFFFFF))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFD54F),
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = "성장 중",
                fontSize = 11.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun StatsRow(
    todayHabit: String,
    streak: String,
    badges: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            icon = Icons.Outlined.WaterDrop,
            label = "오늘의 습관",
            value = todayHabit,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = Icons.Outlined.WbSunny,
            label = "연속 일수",
            value = streak,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(LightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                color = TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

@Composable
fun GrowthTipCard(tip: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TipBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, TipBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "💡", fontSize = 16.sp)
                Text(
                    text = "성장 팁",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = tip,
                fontSize = 13.sp,
                color = Color(0xFF5A6B5A),
                lineHeight = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme { MainScreen(rememberNavController(), viewModel()) }
}