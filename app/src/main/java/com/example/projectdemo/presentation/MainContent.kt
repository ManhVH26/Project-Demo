package com.example.projectdemo.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectdemo.ui.theme.AppTheme
import com.example.projectdemo.ui.theme.ProjectDemoTheme

@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.cardBackground
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Chào mừng đến với Theme Demo!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.brandColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ứng dụng tự động theo theme của thiết bị",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppTheme.colors.onCardBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Thay đổi theme trong Settings > Display của thiết bị để thấy sự khác biệt!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.onCardBackground
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    ProjectDemoTheme {
        MainContent()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainContentDarkPreview() {
    ProjectDemoTheme(darkTheme = true) {
        MainContent()
    }
}