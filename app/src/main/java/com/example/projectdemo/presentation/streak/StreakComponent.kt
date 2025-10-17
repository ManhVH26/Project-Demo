package com.example.projectdemo.presentation.streak

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectdemo.R
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Calendar component showing monthly streak
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StreakCalendar(
    completedDaysList: List<List<Int>>, // List of completed day streaks
    modifier: Modifier = Modifier
) {
    // Current year (2025)
    val currentYear = 2025
    val currentMonth = LocalDate.now().monthValue // Current month (8 for August)
    
    // State for selected month
    var selectedMonth by remember { mutableIntStateOf(currentMonth) }
    
    // Calculate min/max months for current year
    val minMonth = 1
    val maxMonth = 12
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        // Month Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button at start
            IconButton(
                onClick = { 
                    if (selectedMonth > minMonth) {
                        selectedMonth--
                    }
                },
                enabled = selectedMonth > minMonth
            ) {
                Icon(
                    painter = painterResource(R.drawable.button_back_calendar),
                    contentDescription = "Previous Month",
                    modifier = Modifier.size(14.dp),
                    tint = if (selectedMonth > minMonth) Color.Unspecified else Color.Gray
                )
            }

            // Month text in center - dynamic
            val monthName = LocalDate.of(currentYear, selectedMonth, 1)
                .month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            Text(
                text = "$monthName, $currentYear",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2F2F2F)
            )

            // Forward button at end
            IconButton(
                onClick = { 
                    if (selectedMonth < maxMonth) {
                        selectedMonth++
                    }
                },
                enabled = selectedMonth < maxMonth
            ) {
                Icon(
                    painter = painterResource(R.drawable.button_forward_calendar),
                    contentDescription = "Next Month",
                    modifier = Modifier.size(14.dp),
                    tint = if (selectedMonth < maxMonth) Color.Unspecified else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Day labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { day ->
                Text(
                    text = day,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(34.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calendar days - generate based on selected month
        val daysInMonth = LocalDate.of(currentYear, selectedMonth, 1).lengthOfMonth()
        val firstDayOfWeek = LocalDate.of(currentYear, selectedMonth, 1).dayOfWeek.value
        
        // Flatten completedDaysList to get all completed days
        val allCompletedDays = completedDaysList.flatten()
        
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Generate calendar weeks
            var currentDay = 1
            while (currentDay <= daysInMonth) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Generate 7 days for each week
                    repeat(7) { weekDay ->
                        if (currentDay <= daysInMonth) {
                            // Check if this day should be shown (account for first week offset)
                            val shouldShowDay = if (currentDay == 1) weekDay >= firstDayOfWeek - 1 else true
                            
                            if (shouldShowDay) {
                                CalendarDay(
                                    dayNumber = currentDay,
                                    isCompleted = allCompletedDays.contains(currentDay),
                                    isToday = currentDay == LocalDate.now().dayOfMonth && selectedMonth == currentMonth
                                )
                                currentDay++
                            } else {
                                // Empty space for days before month starts
                                Spacer(modifier = Modifier.width(34.dp))
                            }
                        } else {
                            // Empty space for days after month ends
                            Spacer(modifier = Modifier.width(34.dp))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Main Streak Content with Back Button
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StreakContent(
    currentStreak: Int,
    completedDaysList: List<List<Int>>, // List of completed day streaks
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF7F91F9), RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .padding(16.dp)
    ) {
        // Main content
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            StreakHeader(currentStreak = currentStreak)
            Spacer(modifier = Modifier.height(20.dp))
            StreakGoalProgress(currentStreak = currentStreak)
            Spacer(modifier = Modifier.height(20.dp))
            StreakCalendar(completedDaysList = completedDaysList)
        }

        // Back button positioned at top-left corner
        StreakBackButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart)
        )
    }
}

// ==================== PREVIEW FUNCTIONS ====================


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun StreakContentPreview() {
    StreakContent(
        currentStreak = 2,
        completedDaysList = listOf(
            listOf(1, 2, 3), // First streak: days 1-3
            listOf(5, 6),    // Second streak: days 5-6
            listOf(10)       // Third streak: day 10
        ),
        onBackClick = { }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun StreakContentZeroStreakPreview() {
    StreakContent(
        currentStreak = 0,
        completedDaysList = emptyList(),
        onBackClick = { }
    )
}