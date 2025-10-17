package com.example.projectdemo.presentation.streak

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectdemo.R

@Composable
fun CalendarDay(
    dayNumber: Int,
    isCompleted: Boolean,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(34.dp),
        contentAlignment = Alignment.Center
    ) {
        // Day circle
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(
                    if (isCompleted) Color(0xFFFFA400) else Color(0xFFEFF0F1),
                    CircleShape
                )
                .let { boxModifier ->
                    if (isToday && !isCompleted) {
                        boxModifier.border(
                            1.dp,
                            Color(0xFFFFA400),
                            CircleShape
                        )
                    } else {
                        boxModifier
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayNumber.toString(),
                fontSize = 14.sp,
                color = if (isCompleted) Color.White else Color(0xFF6B7280)
            )

            // Checkmark for completed days
            if (isCompleted) {
                Icon(
                    painter = painterResource(R.drawable.checkmark_icon),
                    contentDescription = "Completed",
                    modifier = Modifier
                        .size(12.dp)
                        .offset(x = 11.dp, y = (-11).dp), // Position checkmark in top-right corner
                    tint = Color.Unspecified
                )
            }
        }
    }
}