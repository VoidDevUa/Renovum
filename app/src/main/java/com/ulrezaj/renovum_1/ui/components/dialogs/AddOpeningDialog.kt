package com.ulrezaj.renovum_1.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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

@Composable
fun AddOpeningDialog(
	onDismiss: () -> Unit,
	onConfirm: (OpeningEntity) -> Unit
) {
	var type by remember { mutableStateOf(OpeningType.WINDOW) }
	var width by remember { mutableStateOf("") }
	var height by remember { mutableStateOf("") }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Додати проріз") },
		text = {
			Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
				SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
					OpeningType.entries.forEachIndexed { index, openingType ->
						SegmentedButton(
							selected = type == openingType,
							onClick = { type = openingType },
							shape = SegmentedButtonDefaults.itemShape(index = index, count = OpeningType.entries.size)
						) {
							Text(openingType.displayName)
						}
					}
				}

				OutlinedTextField(
					value = width,
					onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) width = it },
					label = { Text("Ширина (м)") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					singleLine = true
				)

				OutlinedTextField(
					value = height,
					onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) height = it },
					label = { Text("Висота (м)") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
					singleLine = true
				)
			}
		},
		confirmButton = {
			Button(
				enabled = width.isNotBlank() && height.isNotBlank(),
				onClick = {
					onConfirm(OpeningEntity(type.displayName, width, height, type))
				}
			) { Text("Додати") }
		},
		dismissButton = {
			TextButton(onClick = onDismiss) { Text("Скасувати") }
		}
	)
}