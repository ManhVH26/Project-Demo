package com.example.projectdemo.presentation.energy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.projectdemo.presentation.CustomTypography

/**
 * Utility functions for Level management
 * Quy tắc: Level được tính từ Vault Energy tích lũy
 */
object LevelUtils {
    
    /**
     * Bảng energy tích lũy cần thiết cho từng level
     */
    val levelEnergyThresholds = listOf(
        0,     // Level 1
        100,   // Level 2
        300,   // Level 3
        500,   // Level 4
        1000,  // Level 5
        1300,  // Level 6
        1600,  // Level 7
        1900,  // Level 8
        2200,  // Level 9
        2500,  // Level 10
        2800,  // Level 11
        3100,  // Level 12
        3400,  // Level 13
        3700,  // Level 14
        4000,  // Level 15
        4400,  // Level 16
        4800,  // Level 17
        5200,  // Level 18
        5600,  // Level 19
        6000,  // Level 20
        6500,  // Level 21
        7000,  // Level 22
        7500,  // Level 23
        8000,  // Level 24
        8500,  // Level 25
        9100,  // Level 26
        9700,  // Level 27
        10300, // Level 28
        10900, // Level 29
        11500  // Level 30
    )
    
    /**
     * Tính level dựa trên Vault Energy tích lũy
     */
    fun getCurrentLevelFromEnergy(vaultEnergy: Int): Int {
        for (level in levelEnergyThresholds.size downTo 1) {
            if (vaultEnergy >= levelEnergyThresholds[level - 1]) {
                return level
            }
        }
        return 1
    }
    
    /**
     * Lấy energy tích lũy cần thiết để đạt level cụ thể
     */
    fun getEnergyRequiredForLevel(targetLevel: Int): Int {
        if (targetLevel <= 1) return 0
        if (targetLevel > levelEnergyThresholds.size) return levelEnergyThresholds.last()
        return levelEnergyThresholds[targetLevel - 1]
    }
}

val LocalScreenScale = compositionLocalOf { 1f }

val LocalCustomTypography = staticCompositionLocalOf<CustomTypography> {
    error("No typography provided")
}

@Composable
fun Number.pxToDp(): Dp {
    val scale = LocalScreenScale.current
    return (this.toFloat() * scale).dp
}
