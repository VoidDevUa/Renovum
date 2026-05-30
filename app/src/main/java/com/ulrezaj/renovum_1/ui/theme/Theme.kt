@file:Suppress("DEPRECATION")

package com.ulrezaj.renovum_1.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
	primary = DarkText,
	onPrimary = White,
	surface = LighterGray,
	background = White,
	surfaceVariant = LighterGray,
	onSurface = DarkText,
	onBackground = DarkText,
	onSurfaceVariant = DarkText,

	tertiary = LightGreen,
	onTertiary = DarkText,

	secondaryContainer = LightGray,
	onSecondaryContainer = DarkText
)

private val DarkColorScheme = darkColorScheme(
	primary = LightText,
	onPrimary = DeepBlack,
	surface = Graphite,
	background = DeepBlack,
	surfaceVariant = Graphite,
	onSurface = LightText,
	onBackground = LightText,
	onSurfaceVariant = LightText,

	tertiary = DarkGreen,
	onTertiary = LightText,

	secondaryContainer = LightGraphite,
	onSecondaryContainer = LightText
)

@Composable
fun Renovum_1Theme(
	appTheme: AppTheme = AppTheme.SYSTEM,
	content: @Composable () -> Unit
) {
	val darkTheme = when (appTheme) {
		AppTheme.LIGHT -> false
		AppTheme.DARK -> true
		AppTheme.SYSTEM -> isSystemInDarkTheme()
	}

	val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window
			window.statusBarColor = colorScheme.background.toArgb()

			val controller = WindowCompat.getInsetsController(window, view)
			controller.isAppearanceLightStatusBars = !darkTheme
		}
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}