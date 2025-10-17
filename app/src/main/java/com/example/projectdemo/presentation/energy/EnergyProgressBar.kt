package com.example.projectdemo.presentation.energy

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.projectdemo.R
import com.example.projectdemo.presentation.AppColors
import com.example.projectdemo.presentation.CustomTypography
import kotlin.math.roundToInt


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun EnergyProgressBar(
    modifier: Modifier = Modifier,
    energy: Int,
    levels: List<EnergyLevel> = emptyList()
) {
    val currentLevel = remember(energy) {
        LevelUtils.getCurrentLevelFromEnergy(energy)
    }

    val window = remember(currentLevel) {
        buildWindowForLevel(currentLevel)
    }

    val displayLevels = remember(currentLevel, levels) {
        levels.ifEmpty { window.markers }
    }
    val progressRatio = remember(energy, currentLevel, window) {
        computeProgressRatio(
            energy,
            window.e
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Row 1: Top Labels (Energy Values)
        EnergyLevelTopLabelsRow(
            levels = displayLevels
        )

        // Row 2: Progress Bar with Icons
        EnergyLevelProgressBarRow(
            levels = displayLevels,
            progressRatio = progressRatio
        )

        // Row 3: Bottom Labels (Level Numbers)
        EnergyLevelBottomLabelsRow(
            levels = displayLevels
        )
    }
}

@Composable
fun EnergyLevelTopLabelsRow(
    levels: List<EnergyLevel>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.pxToDp())
    ) {
        val density = LocalDensity.current

        levels.forEachIndexed { index, level ->
            val positionRatio = index.toFloat() / (levels.size - 1)
            var labelWidthPx by remember { mutableStateOf(0f) }

            // Modifier đo kích thước label
            val measureModifier = Modifier.onGloballyPositioned {
                labelWidthPx = it.size.width.toFloat()
            }

            // Tính toán offsetX động theo tỷ lệ
            EnergyLevelTopLabel(
                energyValue = level.value,
                isActive = level.isActive,
                modifier = measureModifier.then(
                    Modifier.offset {
                        val maxWidthPx = constraints.maxWidth.toFloat()
                        val posX = (maxWidthPx * positionRatio) - labelWidthPx / 2
                        IntOffset(posX.roundToInt(), 0)
                    }
                )
            )
        }
    }
}

@Composable
fun EnergyLevelProgressBarRow(
    levels: List<EnergyLevel>,
    progressRatio: Float,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.pxToDp())
            .wrapContentHeight()
    ) {
        // Background progress line
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(10.pxToDp())
                .background(AppColors.Background.Tag)
        )

        // Current progress line
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(progressRatio)
                .height(10.pxToDp())
                .background(AppColors.BrandPallete.Primary)
        )

        // Level icons
        levels.forEachIndexed { index, level ->
            val positionRatio = index.toFloat() / (levels.size - 1)
            val iconSize = if (index == levels.lastIndex) 38.pxToDp() else 16.pxToDp()
            val halfIcon = iconSize / 2
            val offsetX = (maxWidth * positionRatio) - halfIcon

            EnergyLevelIcon(
                energyLevel = level,
                isLastLevel = index == levels.lastIndex,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = offsetX)
            )
        }

        // Current progress marker
        val imageSize = 52.pxToDp()
        val halfImage = imageSize / 2
        val offsetX = (maxWidth * progressRatio) - halfImage

        Image(
            painter = painterResource(R.drawable.img_your_path_current_progress),
            contentDescription = null,
            modifier = Modifier
                .size(imageSize)
                .offset(x = offsetX)
        )
    }
}

@Composable
fun EnergyLevelBottomLabelsRow(
    levels: List<EnergyLevel>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.pxToDp())
    ) {
        levels.forEachIndexed { index, level ->
            val positionRatio = index.toFloat() / (levels.size - 1)
            var labelWidthPx by remember { mutableStateOf(0f) }

            val measureModifier = Modifier.onGloballyPositioned {
                labelWidthPx = it.size.width.toFloat()
            }

            EnergyLevelBottomLabel(
                level = level.level.toInt(),
                isActive = level.isActive,
                modifier = measureModifier.then(
                    Modifier.offset {
                        val maxWidthPx = constraints.maxWidth.toFloat()
                        val posX = (maxWidthPx * positionRatio) - labelWidthPx / 2
                        IntOffset(posX.roundToInt(), 0)
                    }
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EnergyProgressBarPreview() {
    CompositionLocalProvider(
        LocalScreenScale provides 1f,
        LocalCustomTypography provides CustomTypography.Companion.create(
            scale = 1f,
            fontFamily = FontFamily.Default
        )
    ) {
        EnergyProgressBar(
            energy = 0
        )
    }
}