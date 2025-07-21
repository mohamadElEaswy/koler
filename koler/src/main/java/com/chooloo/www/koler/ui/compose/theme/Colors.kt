package com.chooloo.www.koler.ui.compose.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    // Primary colors
    val primaryDark = Color(0xFF340169)
    val primary = Color(0xFF4057c0)
    val grey = Color(0xFF929292)
    val textGrey = Color(0xFF717171)
    val greyBorder = Color(0xFFEBE6F0)
    val lightGrey = Color(0xFFCDCDCD)
    val golden = Color(0xFFFFB800)
    val white = Color(0xFFFFFFFF)
    val black = Color(0xFF000000)
    val accent = Color(0xFFFF6B35) // Orange accent color for subscription tab
    val red = Color(0xFF800000)
    val lightGreen = Color(0xFFEBF3F0)
    val green = Color(0xFF378A6D)
    val darkGreen = Color(0xFF27624D)
    val inactiveTrackColor = Color(0xFFEBE6F0)
}

object LinearColors {
    val darkLinear = listOf(AppColors.primary, AppColors.primaryDark)
}