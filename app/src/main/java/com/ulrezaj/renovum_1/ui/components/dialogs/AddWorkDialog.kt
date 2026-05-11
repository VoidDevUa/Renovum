package com.ulrezaj.renovum_1.ui.components.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.model.WorkService

@SuppressLint("DefaultLocale")
@Composable
fun AddWorkDialog(
	work: WorkService,
	roomName: String,
	onDismiss: () -> Unit,
	onSave: (customPrice: Double, volume: Double) -> Unit
) {
	var customPrice by remember { mutableStateOf("") }
	var volume by remember { mutableStateOf("") }

	val finalPrice = customPrice.toDoubleOrNull() ?: work.averagePrice
	val finalVolume = volume.toDoubleOrNull() ?: 1.0
	val totalSum = finalPrice * finalVolume

	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(onClick = { onSave(finalPrice, finalVolume) }) {
				Text("Зберегти", color = MaterialTheme.colorScheme.primary)
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Скасувати", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
			}
		},
		title = {
			Column {
				Text(roomName, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
				Text(work.name, style = MaterialTheme.typography.titleLarge)
			}
		},
		text = {
			Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
				if (work.minPrice > 0.0 || work.maxPrice > 0.0) {
					Text(
						text = "Діапазон: ${work.minPrice.toInt()} — ${work.maxPrice.toInt()} грн / ${work.unit.displayName}",
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
					)
				}
				OutlinedTextField(
					value = customPrice,
					onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) customPrice = it },
					label = { Text("Ціна за одиницю") },
					placeholder = {
						if (work.averagePrice > 0.0) {
							Text("${work.averagePrice.toInt()}")
						}
					},
					suffix = { Text("грн/${work.unit.displayName}") },
					singleLine = true,
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					modifier = Modifier.fillMaxWidth()
				)
				OutlinedTextField(
					value = volume,
					onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) volume = it },
					label = { Text("Об'єм робіт") },
					placeholder = { Text("1.0") },
					suffix = { Text(work.unit.displayName) },
					singleLine = true,
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					modifier = Modifier.fillMaxWidth()
				)
				Card(
					modifier = Modifier.fillMaxWidth(),
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
					)
				) {
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(12.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text("Разом:", style = MaterialTheme.typography.bodyMedium)
						Text(
							text = "${String.format("%.2f", totalSum)} ₴",
							style = MaterialTheme.typography.titleMedium,
							color = MaterialTheme.colorScheme.primary
						)
					}
				}
			}
		}
	)
}