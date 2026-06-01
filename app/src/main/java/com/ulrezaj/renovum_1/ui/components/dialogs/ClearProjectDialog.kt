package com.ulrezaj.renovum_1.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ClearProjectDialog(
	onDismiss: () -> Unit,
	onConfirm: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Очистити поточний об'єкт?", style = MaterialTheme.typography.titleLarge) },
		text = {
			Text(
				text = "Усі додані кімнати та виконані роботи будуть безповоротно очищені. Створити новий об'єкт?",
				style = MaterialTheme.typography.bodyMedium
			)
		},
		confirmButton = {
			TextButton(onClick = onConfirm) {
				Text("Очистити", color = MaterialTheme.colorScheme.error)
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Залишити")
			}
		}
	)
}