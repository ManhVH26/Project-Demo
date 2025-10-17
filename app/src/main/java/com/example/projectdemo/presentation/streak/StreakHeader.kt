package com.example.projectdemo.presentation.streak

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectdemo.R

@Composable
fun StreakHeader(
    currentStreak: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        // Streak Icon
        Icon(
            painter = painterResource(if (currentStreak > 0) R.drawable.have_streak_icon else R.drawable.no_streak_icon),
            contentDescription = "Streak Icon",
            modifier = Modifier.size(133.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Streak Title
        Text(
            text = "$currentStreak day streak!",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = if (currentStreak > 0) Color(0xFFFDD908) else Color(0xFFEAEAEA),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Streak Message
        val message = if (currentStreak > 0) {
            "Congratulations! You have the longest streak ever!"
        } else {
            "Start your streak with 1 exercise."
        }

        Text(
            text = message,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}