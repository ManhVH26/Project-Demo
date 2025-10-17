package com.example.projectdemo.presentation

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp


data class TitleTypography(
    val bold: TextStyle,
    val semiBold: TextStyle,
    val medium: TextStyle,
    val regular: TextStyle,
    val blackItalic: TextStyle,
    val heavy: TextStyle
)

abstract class CustomTypography {
    abstract val LargeTitleSpecial01: TitleTypography
    abstract val LargeTitleSpecial02: TitleTypography
    abstract val LargeTitleSpecial03: TitleTypography
    abstract val LargeTitleSpecial04: TitleTypography
    abstract val LargeTitle: TitleTypography
    abstract val Title1: TitleTypography
    abstract val Title2: TitleTypography
    abstract val Title3: TitleTypography
    abstract val Headline: TitleTypography
    abstract val Body: TitleTypography
    abstract val Footnote: TitleTypography
    abstract val Caption1: TitleTypography
    abstract val Caption2: TitleTypography
    abstract val Caption1UP: TitleTypography
    abstract val Caption2UP: TitleTypography
    abstract val BlackItalic: TitleTypography
    abstract val BlackItalic2: TitleTypography
    abstract val BlackItalic3: TitleTypography
    abstract val LeadingIcon: TitleTypography
    abstract val LargeIcon: TitleTypography

    companion object {
        fun create(scale: Float, fontFamily: FontFamily): CustomTypography {
            fun textStyle(weight: FontWeight, size: Float, lineHeight: Float) = TextStyle(
                fontSize = (size * scale).sp,
                lineHeight = (lineHeight * scale).sp,
                fontWeight = weight,
                fontFamily = fontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                )
            )

            fun titleGroup(size: Float, lineHeight: Float) = TitleTypography(
                bold = textStyle(FontWeight.Bold, size, lineHeight),
                semiBold = textStyle(FontWeight.SemiBold, size, lineHeight),
                medium = textStyle(FontWeight.Medium, size, lineHeight),
                regular = textStyle(FontWeight.Normal, size, lineHeight),
                blackItalic = textStyle(FontWeight.ExtraBold, size, lineHeight),
                heavy = textStyle(FontWeight.Black, size, lineHeight)
            )

            return object : CustomTypography() {
                override val LargeTitleSpecial01 = titleGroup(100f, 120f)
                override val LargeTitleSpecial02 = titleGroup(52f, 62f)
                override val LargeTitleSpecial03 = titleGroup(120f, 140f)
                override val LargeTitleSpecial04 = titleGroup(70f, 70f)
                override val BlackItalic = titleGroup(32f, 36f)
                override val BlackItalic2 = titleGroup(24f, 26f)
                override val BlackItalic3 = titleGroup(44f, 44f)
                override val LargeTitle = titleGroup(34f, 41f)
                override val LeadingIcon = titleGroup(32f, 34f)
                override val LargeIcon = titleGroup(58f, 60f)
                override val Title1 = titleGroup(28f, 34f)
                override val Title2 = titleGroup(22f, 28f)
                override val Title3 = titleGroup(20f, 25f)
                override val Headline = titleGroup(18f, 24f)
                override val Body = titleGroup(16f, 22f)
                override val Footnote = titleGroup(14f, 20f)
                override val Caption1 = titleGroup(12f, 16f)
                override val Caption2 = titleGroup(11f, 12f)
                override val Caption1UP = titleGroup(12f, 16f)
                override val Caption2UP = titleGroup(11f, 12f)
            }
        }
    }
}