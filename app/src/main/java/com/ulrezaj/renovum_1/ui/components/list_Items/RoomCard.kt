package com.ulrezaj.renovum_1.ui.components.list_Items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.model.RoomShapeType
import com.ulrezaj.renovum_1.ui.theme.Renovum_1Theme

@Composable
fun RoomCard(
	name: String,
	shapeType: RoomShapeType,
	dimensions: String,
	showDimensions: Boolean = true,
	isEditMode: Boolean = false,
	onDeleteClick: () -> Unit = {},
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 8.dp)
			.clickable { if (!isEditMode) onClick() },
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.tertiary,
			contentColor = MaterialTheme.colorScheme.onTertiary
		),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
	) {
		Row(
			modifier = Modifier.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			if (isEditMode) {
				IconButton(onClick = onDeleteClick) {
					Icon(
						imageVector = Icons.Default.Delete,
						contentDescription = "Видалити",
						tint = MaterialTheme.colorScheme.error
					)
				}
				Spacer(modifier = Modifier.width(8.dp))
			}

			Column(modifier = Modifier.weight(1f)) {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(text = name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

					val shapeLabel = when(shapeType) {
						RoomShapeType.RECTANGLE -> "Прямокутна"
						RoomShapeType.L_SHAPED -> "Г-подібна"
						RoomShapeType.T_SHAPED -> "Т-подібна"
					}
					Text(text = shapeLabel, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.8f))
				}
				if (showDimensions) {
					Text(text = dimensions, style = MaterialTheme.typography.bodyMedium)
				}
			}
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RoomCardPreview() {
	Renovum_1Theme {
		Column {
			// З розмірами
			RoomCard(
				name = "Кухня",
				shapeType = RoomShapeType.RECTANGLE,
				dimensions = "4.0 х 3.0 м",
				showDimensions = true,
				onClick = { }
			)
			// Без розмірів (тільки назва і форма)
			RoomCard(
				name = "Спальня",
				shapeType = RoomShapeType.RECTANGLE,
				dimensions = "5.0 х 4.0 м",
				showDimensions = false,
				onClick = { }
			)
		}
	}
}