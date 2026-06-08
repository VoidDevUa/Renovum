package com.void_dev_ua.renovum.ui.screens.done_screen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
@Composable
fun DoneRoomCard(
	roomName: String,
	roomTotal: Double,
	isExpanded: Boolean,
	onExpandClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 4.dp),
		onClick = onExpandClick,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
		)
	) {
		Row(
			modifier = Modifier
				.padding(12.dp)
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Column(modifier = Modifier.weight(1f)) {
				Text(
					text = roomName,
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSecondaryContainer
				)
				Text(
					text = "Підсумок: ${String.format("%.2f", roomTotal)} ₴",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
				)
			}

			Icon(
				imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
				contentDescription = if (isExpanded) "Згорнути" else "Розгорнути",
				tint = MaterialTheme.colorScheme.secondary
			)
		}
	}
}