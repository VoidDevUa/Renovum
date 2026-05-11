package com.ulrezaj.renovum_1.ui.components.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
@Composable
fun DiscountDialog(
	initialDiscount: Double,
	totalRawSum: Double,
	onDismiss: () -> Unit,
	onConfirm: (Double) -> Unit
) {
	var discountInput by remember { mutableStateOf(if (initialDiscount > 0) initialDiscount.toString() else "") }

	val currentDiscount = discountInput.toDoubleOrNull() ?: 0.0
	val discountedSum = totalRawSum * (1.0 - currentDiscount / 100.0)

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Редагування знижки") },
		text = {
			Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
				Column {
					Text("Загальна сума: ${String.format("%.2f", totalRawSum)} ₴",
						style = MaterialTheme.typography.bodyMedium)
					Text(
						text = "Сума після знижки: ${String.format("%.2f", discountedSum)} ₴",
						style = MaterialTheme.typography.titleMedium,
						color = MaterialTheme.colorScheme.primary,
						fontWeight = FontWeight.Bold
					)
				}

				OutlinedTextField(
					value = discountInput,
					onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null) discountInput = it },
					label = { Text("Знижка (%)") },
					placeholder = { Text("Поточна: ${initialDiscount.toInt()}%") },
					suffix = { Text("%") },
					modifier = Modifier.fillMaxWidth(),
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					singleLine = true
				)
			}
		},
		confirmButton = {
			TextButton(onClick = { onConfirm(currentDiscount) }) {
				Text("Зберегти")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Скасувати")
			}
		}
	)
}