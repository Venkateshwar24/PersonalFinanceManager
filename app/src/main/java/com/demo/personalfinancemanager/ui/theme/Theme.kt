package com.demo.personalfinancemanager.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark color scheme for the Personal Finance Manager app
 */
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = TextOnDark,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextOnDark,
    
    secondary = Secondary,
    onSecondary = TextOnDark,
    
    background = BackgroundDark,
    onBackground = TextOnDark,
    
    surface = SurfaceDark,
    onSurface = TextOnDark,
    surfaceVariant = PrimaryLight,
    onSurfaceVariant = TextOnDarkSecondary,
    
    error = DebitRed,
    onError = TextOnDark,
    
    outline = DividerColor,
    outlineVariant = Color(0xFF424242)
)

/**
 * Light color scheme for the Personal Finance Manager app
 * Based on the screenshot design
 */
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnDark,
    primaryContainer = BackgroundLight,
    onPrimaryContainer = TextPrimary,
    
    secondary = Secondary,
    onSecondary = TextOnDark,
    
    background = BackgroundLight,
    onBackground = TextPrimary,
    
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundLight,
    onSurfaceVariant = TextSecondary,
    
    error = DebitRed,
    onError = TextOnDark,
    
    outline = DividerColor,
    outlineVariant = Color(0xFFE0E0E0)
)

/**
 * Main theme composable for the Personal Finance Manager app
 * 
 * @param darkTheme Whether to use dark theme (defaults to system setting)
 * @param dynamicColor Whether to use Material You dynamic colors on Android 12+
 * @param content The content to be themed
 */
@Composable
fun PersonalFinanceManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ but we'll disable it for consistent branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
