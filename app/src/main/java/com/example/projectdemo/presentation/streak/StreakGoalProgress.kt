package com.example.projectdemo.presentation.streak

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectdemo.R

@Composable
fun StreakGoalProgress(currentStreak: Int, modifier: Modifier = Modifier) {
    val iconSize = 32.dp
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        HeaderStreakGoalProgress(currentStreak)
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        ) {
            val currentWeek = (currentStreak / 7) + 1
            val weekStart = (currentWeek - 1) * 7
            val weekEnd = currentWeek * 7
            val currentDayInWeek = (currentStreak - weekStart).coerceIn(0, 7)
            val weekProgress = currentDayInWeek / 7f

            // Progress background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .offset(y = 9.dp)
                    .background(Color(0xFFE7E7E7), RoundedCornerShape(7.dp))
            )

            // Progress fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(weekProgress)
                    .height(10.dp)
                    .offset(y = 9.dp)
                    .background(Color(0xFFF8972C), RoundedCornerShape(7.dp))
            ) {
                if (weekProgress > 0f) {
                    Icon(
                        painter = painterResource(R.drawable.point_of_progress_streak),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterEnd)
                            .offset(x = 4.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            // --- Fire icons ---
            val milestones = listOf(0f, 2 / 7f, 4 / 7f, 1f)
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                val boxWidthPx = constraints.maxWidth.toFloat()

                milestones.forEachIndexed { i, ratio ->
                    val milestoneNumber = when (i) {
                        0 -> weekStart
                        1 -> weekStart + 2
                        2 -> weekStart + 4
                        else -> weekEnd
                    }

                    val xOffsetDp = with(density) {
                        (boxWidthPx * ratio).toDp() - iconSize / 2
                    }

                    val finalOffset = if (i == 0) -iconSize / 2 else xOffsetDp

                    Box(
                        modifier = Modifier
                            .offset(x = finalOffset)
                            .align(Alignment.CenterStart)
                    ) {
                        FireIconWithNumber(
                            number = milestoneNumber,
                            isActive = currentStreak >= milestoneNumber
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun HeaderStreakGoalProgress(currentStreak: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "STREAK GOAL",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFF8972C)
        )

        val currentWeek = (currentStreak / 7) + 1
        val weekEnd = currentWeek * 7

        Text(
            text = "$currentStreak/$weekEnd day streak",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4B5563)
        )
    }
}