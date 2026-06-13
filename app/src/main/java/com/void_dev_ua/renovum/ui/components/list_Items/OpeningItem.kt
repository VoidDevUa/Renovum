package com.void_dev_ua.renovum.ui.components.list_Items

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.model.OpeningEntity
import com.void_dev_ua.renovum.model.OpeningType

@Composable
fun OpeningItem(
	opening: OpeningEntity,
	onDeleteClick: () -> Unit
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
	) {
		Row(
			modifier = Modifier.padding(12.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Icon(
				imageVector = if (opening.type == OpeningType.WINDOW) Icons.Default.Window else Icons.Default.DoorFront,
				contentDescription = null,
				tint = MaterialTheme.colorScheme.primary
			)

			Column(modifier = Modifier.weight(1f)) {
				Text(text = opening.label, fontWeight = FontWeight.Bold)
				Text(
					text = "${opening.width} м x ${opening.height} м",
					style = MaterialTheme.typography.bodySmall
				)
			}

			IconButton(onClick = onDeleteClick) {
				Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
			}
		}
	}
}