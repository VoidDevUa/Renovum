package com.void_dev_ua.renovum.ui.screens.rooms_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun NewProjectDialog(
	onDismiss: () -> Unit,
	onConfirm: (String) -> Unit
) {
	val addressInput = remember { mutableStateOf("") }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Новий об'єкт", style = MaterialTheme.typography.titleLarge) },
		text = {
			OutlinedTextField(
				value = addressInput.value,
				onValueChange = { addressInput.value = it },
				label = { Text("Введіть адресу ремонту") },
				placeholder = { Text("напр. вул. Хрещатик, 12") },
				modifier = Modifier.fillMaxWidth(),
				singleLine = true
			)
		},
		confirmButton = {
			TextButton(
				onClick = { onConfirm(addressInput.value) },
				enabled = addressInput.value.isNotBlank()
			) {
				Text("Почати")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Скасувати")
			}
		}
	)
}