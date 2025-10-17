package com.example.projectdemo.presentation.energy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.projectdemo.R
import com.example.projectdemo.presentation.AppColors

data class EnergyLevel(
    val value: Int,
    val level: String,
    val isActive: Boolean = false
)

data class WindowData(
    val startLevel: Int,
    val markers: List<EnergyLevel>, // 5 markers, last is target (label = window end level)
    val e: List<Int>, // energies for markers (size 5)
)

/**
 * Calculate window start and end levels for a given current level
 * Dynamic windows: each window has 5 levels with overlap at every 4th level
 * Pattern: 1-5, 5-9, 9-13, 13-17, 17-21, 21-25, 25-29, 29-33, 33-37, etc.
 */
fun calculateWindowForLevel(currentLevel: Int): Pair<Int, Int> {
    // Get max level from LevelUtils
    val maxLevel = LevelUtils.levelEnergyThresholds.size

    // Calculate window boundaries dynamically
    val windowBoundaries = generateWindowBoundaries(maxLevel)

    // Find the appropriate window for the current level
    // When level is at overlap point (5, 9, 13, etc.), prefer the higher window
    for ((start, end) in windowBoundaries.reversed()) {
        if (currentLevel in start..end) {
            return start to end
        }
    }

    // Fallback to last window if level is beyond max
    return windowBoundaries.last()
}

/**
 * Generate window boundaries dynamically based on max level
 * Each window has 5 levels with overlap at every 4th level
 */
fun generateWindowBoundaries(maxLevel: Int): List<Pair<Int, Int>> {
    val boundaries = mutableListOf<Pair<Int, Int>>()

    // Start from level 1, create windows of 5 levels with 4-level overlap
    var start = 1
    while (start <= maxLevel) {
        val end = minOf(start + 4, maxLevel) // 5 levels per window
        boundaries.add(start to end)

        // Next window starts 4 levels later (overlap at 5th level)
        start += 4
    }

    return boundaries
}

fun buildWindowForLevel(currentLevel: Int): WindowData {
    // Calculate which window group this level belongs to (dynamic windows)
    val (windowStartLevel, windowEndLevel) = calculateWindowForLevel(currentLevel)

    // Create markers for the 5 levels in this window
    val markers = (windowStartLevel..windowEndLevel).map { level ->
        EnergyLevel(
            value = LevelUtils.getEnergyRequiredForLevel(level),
            level = "$level",
            isActive = level <= currentLevel  // Tất cả level <= currentLevel đều active
        )
    }

    val energies = (windowStartLevel..windowEndLevel).map { level ->
        LevelUtils.getEnergyRequiredForLevel(level)
    }

    return WindowData(
        startLevel = windowStartLevel,
        markers = markers,
        e = energies
    )
}

fun computeProgressRatio(energy: Int, energies: List<Int>): Float {
    if (energies.size < 5) return 0f

    if (energies.zipWithNext().any { it.second <= it.first }) return 0f

    if (energy <= energies.first()) return 0f
    if (energy >= energies.last()) return 1f

    var segmentIndex = 0
    for (i in 0 until energies.lastIndex) {
        if (energy < energies[i + 1]) {
            segmentIndex = i
            break
        }
    }

    val eStart = energies[segmentIndex]
    val eEnd = energies[segmentIndex + 1]
    val segmentProgress = (energy - eStart).toFloat() / (eEnd - eStart)

    val ratioPerSegment = 1f / (energies.size - 1)
    val totalRatio = (segmentIndex * ratioPerSegment) + (segmentProgress * ratioPerSegment)

    return totalRatio.coerceIn(0f, 1f)
}

@Composable
fun EnergyLevelTopLabel(
    modifier: Modifier = Modifier,
    energyValue: Int,
    isActive: Boolean,
    isFirstLevel: Boolean = false,
    isLastLevel: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text giá trị năng lượng
        Text(
            text = "⚡ $energyValue",
            style = if (isActive)
                LocalCustomTypography.current.Footnote.semiBold
            else
                LocalCustomTypography.current.Footnote.medium,
            color = AppColors.Text.SubTitle,
        )

        if (!isFirstLevel && !isLastLevel) {
            Image(
                painter = painterResource(R.drawable.ic_your_path_level_line),
                contentDescription = null,
                modifier = Modifier
                    .height(26.pxToDp())
                    .wrapContentWidth()
            )
        } else {
            Spacer(Modifier.height(26.pxToDp()))
        }
    }
}


@Composable
fun EnergyLevelIcon(
    energyLevel: EnergyLevel,
    modifier: Modifier = Modifier,
    isLastLevel: Boolean = false,
) {
    val iconSize = if (isLastLevel) 38.pxToDp() else 16.pxToDp()

    Image(
        painter = when {
            isLastLevel -> painterResource(R.drawable.img_your_path_target_progress)
            energyLevel.isActive -> painterResource(R.drawable.ic_your_path_level_active)
            else -> painterResource(R.drawable.ic_your_path_level_no_active)
        },
        contentDescription = null,
        modifier = modifier.size(iconSize)
    )
}


@Composable
fun EnergyLevelBottomLabel(
    level: Int,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Lv $level",
        style = if (isActive)
            LocalCustomTypography.current.Footnote.semiBold
        else
            LocalCustomTypography.current.Footnote.regular,
        color = AppColors.Text.Content,
        modifier = modifier
    )
}

