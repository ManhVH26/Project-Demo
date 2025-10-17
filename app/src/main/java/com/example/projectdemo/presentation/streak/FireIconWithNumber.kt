package com.example.projectdemo.presentation.streak

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectdemo.R

@Composable
fun FireIconWithNumber(
    number: Int,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(if (isActive) R.drawable.fire_streak_icon_active else R.drawable.fire_streak_icon_no_active),
            contentDescription = "Fire Icon",
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )

        Text(
            text = number.toString(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 6.dp, start = 3.dp)
        )
    }
}