package com.example.dailybloom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
private val BarColor        = Color(0xFF6BBF85)

@Composable
fun ReportScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val weekData = listOf(
        Pair("월", 0.65f),
        Pair("화", 0.90f),
        Pair("수", 0.40f),
        Pair("목", 0.80f),
        Pair("금", 0.60f),
        Pair("토", 0.88f),
        Pair("일", 0.85f),
    )

    val categories = listOf(
        CategoryItem(Icons.Outlined.WaterDrop, "수분 섭취", "26/30 완료 · 87%"),
        CategoryItem(Icons.Outlined.Air,       "환기",     "22/30 완료 · 73%"),
        CategoryItem(Icons.Outlined.DirectionsWalk, "스트레칭", "20/30 완료 · 67%"),
    )

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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "월간 리포트",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = "2026년 4월",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryGreen),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "성공률",
                            fontSize = 14.sp,
                            color = Color(0xCCFFFFFF)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "82%",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "이번 달 94개의 습관을 완료했어요",
                            fontSize = 13.sp,
                            color = Color(0xCCFFFFFF)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0x33FFFFFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.TrendingUp,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "이번 주 진행 상황",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(16.dp))
                    WeekBarChart(data = weekData)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "카테고리별 상세",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(Modifier.height(10.dp))

            categories.forEach { category ->
                CategoryCard(item = category)
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun WeekBarChart(data: List<Pair<String, Float>>) {
    val maxHeight = 100.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeight + 200.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { (label, fraction) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .width(26.dp)
                        .fillMaxHeight(fraction)
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(BarColor)
                )
                Spacer(Modifier.height(6.dp))
                Text(text = label, fontSize = 11.sp, color = TextSecondary)
            }
        }
    }
}

private data class CategoryItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

@Composable
private fun CategoryCard(item: CategoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                Spacer(Modifier.height(2.dp))
                Text(text = item.subtitle, fontSize = 12.sp, color = TextSecondary)
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportScreenPreview() {
    MaterialTheme { ReportScreen(rememberNavController(), viewModel()) }
}
