package com.ulrezaj.renovum_1.ui.components.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.WorkService

@SuppressLint("DefaultLocale")
@Composable
fun EditWorkDialog(
	appliedWork: AppliedWork,
	service: WorkService,
	onDismiss: () -> Unit,
	onConfirm: (Double, Double) -> Unit,
	onDelete: () -> Unit
) {
	var priceInput by remember { mutableStateOf(appliedWork.priceAtTime.toString()) }
	var qtyInput by remember { mutableStateOf(appliedWork.quantity.toString()) }

	val currentPrice = priceInput.toDoubleOrNull() ?: appliedWork.priceAtTime
	val currentQty = qtyInput.toDoubleOrNull() ?: appliedWork.quantity

	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(onClick = { onConfirm(currentPrice, currentQty) }) {
				Text("Зберегти", color = MaterialTheme.colorScheme.primary)
			}
		},
		dismissButton = {
			Row(verticalAlignment = Alignment.CenterVertically) {
				IconButton(onClick = onDelete) {
					Icon(
						Icons.Default.Delete,
						contentDescription = "Видалити",
						tint = MaterialTheme.colorScheme.error
					)
				}
				TextButton(onClick = onDismiss) {
					Text("Скасувати", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
				}
			}
		},
		title = {
			Column {
				Text("Редагування", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
				Text(service.name, style = MaterialTheme.typography.titleLarge)
			}
		},
		text = {
			Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
				if (service.minPrice > 0.0 || service.maxPrice > 0.0) {
					Text(
						text = "Діапазон: ${service.minPrice.toInt()} — ${service.maxPrice.toInt()} грн / ${service.unit.displayName}",
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
					)
				}
				OutlinedTextField(
					value = priceInput,
					onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) priceInput = it },
					label = { Text("Ціна за одиницю") },
					suffix = { Text("грн/${service.unit.displayName}") },
					singleLine = true,
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					modifier = Modifier.fillMaxWidth()
				)
				OutlinedTextField(
					value = qtyInput,
					onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) qtyInput = it },
					label = { Text("Об'єм робіт") },
					suffix = { Text(service.unit.displayName) },
					singleLine = true,
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	)
}