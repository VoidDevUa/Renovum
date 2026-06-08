package com.void_dev_ua.renovum.ui.components.topAppBar.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
@Composable
fun TotalSumDisplay(
	totalSum: Double,
	onClick: () -> Unit
) {
	Surface(
		onClick = onClick,
		color = MaterialTheme.colorScheme.primaryContainer,
		shape = MaterialTheme.shapes.medium
	) {
		Text(
			text = "${String.format("%.0f", totalSum)} ₴",
			modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onPrimaryContainer,
			fontWeight = FontWeight.Bold
		)
	}
}