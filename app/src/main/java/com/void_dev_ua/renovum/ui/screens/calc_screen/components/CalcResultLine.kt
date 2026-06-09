package com.void_dev_ua.renovum.ui.screens.calc_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CalcResultLine(label: String, value: String, isMain: Boolean = false) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
		Text(
			text = label,
			style = if (isMain) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
			fontWeight = if (isMain) FontWeight.ExtraBold else FontWeight.Normal
		)
		Text(
			text = value,
			style = if (isMain) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyLarge,
			fontWeight = FontWeight.Bold,
			color = if (isMain) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
		)
	}
}