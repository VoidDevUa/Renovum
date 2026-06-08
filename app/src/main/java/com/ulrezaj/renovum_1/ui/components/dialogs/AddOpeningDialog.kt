package com.ulrezaj.renovum_1.ui.components.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.model.OpeningEntity
import com.ulrezaj.renovum_1.data.model.OpeningType
import com.ulrezaj.renovum_1.utility.L

@SuppressLint("DefaultLocale")
@Composable
fun AddOpeningDialog(
	maxHeight: Double,
	onDismiss: () -> Unit,
	onConfirm: (OpeningEntity) -> Unit
) {
	var type by remember { mutableStateOf(OpeningType.WINDOW) }
	var width by remember { mutableStateOf("") }
	var height by remember { mutableStateOf("") }

	val numericWidth = width.toDoubleOrNull() ?: 0.0
	val numericHeight = height.toDoubleOrNull() ?: 0.0

	val isHeightError = numericHeight > maxHeight
	val isInputValid = width.isNotBlank() && height.isNotBlank() &&
			numericWidth > 0.0 && numericHeight > 0.0 && !isHeightError

	AlertDialog(
		onDismissRequest = {
			L.d("OpeningDialog: Dismissed by request")
			onDismiss()
		},
		title = { Text("Додати проріз") },
		text = {
			Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
				SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
					OpeningType.entries.forEachIndexed { index, openingType ->
						SegmentedButton(
							selected = type == openingType,
							onClick = {
								L.click("OpeningDialog: Change type to ${openingType.displayName}")
								type = openingType
							},
							shape = SegmentedButtonDefaults.itemShape(index = index, count = OpeningType.entries.size)
						) {
							Text(openingType.displayName)
						}
					}
				}

				OutlinedTextField(
					value = width,
					onValueChange = { newValue ->
						if (newValue.length <= 5 && newValue.all { c -> c.isDigit() || c == '.' }) {
							width = newValue
						}
					},
					label = { Text("Ширина (м)") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					singleLine = true,
					modifier = Modifier.fillMaxWidth()
				)

				OutlinedTextField(
					value = height,
					onValueChange = { newValue ->
						if (newValue.length <= 5 && newValue.all { c -> c.isDigit() || c == '.' }) {
							height = newValue
						}
					},
					label = { Text("Висота (м)") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					singleLine = true,
					isError = isHeightError,
					supportingText = {
						if (isHeightError) {
							Text(
								text = "Висота прорізу не може перевищувати висоту стелі (${String.format("%.2f", maxHeight)} м)",
								color = MaterialTheme.colorScheme.error
							)
						}
					},
					modifier = Modifier.fillMaxWidth()
				)
			}
		},
		confirmButton = {
			Button(
				enabled = isInputValid, // Кнопка активна тільки якщо все ок
				onClick = {
					L.click("OpeningDialog: Confirming $type ${width}x${height}")
					onConfirm(OpeningEntity(type.displayName, width, height, type))
				}
			) { Text("Додати") }
		},
		dismissButton = {
			TextButton(onClick = {
				L.click("OpeningDialog: Cancel clicked")
				onDismiss()
			}) { Text("Скасувати") }
		}
	)
}