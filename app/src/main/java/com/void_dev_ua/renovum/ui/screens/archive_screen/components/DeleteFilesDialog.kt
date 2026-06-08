package com.void_dev_ua.renovum.ui.screens.archive_screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteFilesDialog(
	selectedCount: Int,
	onConfirm: () -> Unit,
	onDismiss: () -> Unit
){
	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Видалення файлів") },
		text = {
			Text("Ви впевнені, що хочете остаточно видалити вибрані кошториси ($selectedCount шт.) з архіву додатка?")
		},
		confirmButton = {
			TextButton(
				onClick = onConfirm
			) {
				Text("Видалити", color = MaterialTheme.colorScheme.error)
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Скасувати")
			}
		}
	)
}