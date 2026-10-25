package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = TurfGreenPrimary,
    onPrimary = Color(0xFF041E11),
    primaryContainer = PitchCardHover,
    onPrimaryContainer = TurfGreenLight,

    secondary = TrophyGold,
    onSecondary = Color(0xFF241A00),
    secondaryContainer = TrophyGoldContainer,
    onSecondaryContainer = TrophyGold,

    tertiary = TurfGreenLight,
    onTertiary = Color(0xFF00381E),

    background = PitchDark,
    onBackground = TextPrimary,

    surface = PitchCard,
    onSurface = TextPrimary,
    surfaceVariant = PitchDarkElevated,
    onSurfaceVariant = TextSecondary,

    outline = PitchCardBorder,
    outlineVariant = DividerGreen,

    error = AlertRed,
    onError = Color.White
)

@Composable
fun FootballPronoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
