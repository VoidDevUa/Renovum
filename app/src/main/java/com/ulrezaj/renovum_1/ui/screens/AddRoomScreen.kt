package com.ulrezaj.renovum_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.navigation.NavHostController
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.OpeningEntity
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.data.model.RoomParams
import com.ulrezaj.renovum_1.data.model.RoomShapeType
import com.ulrezaj.renovum_1.ui.components.dialogs.AddOpeningDialog
import com.ulrezaj.renovum_1.ui.components.dialogs.RoomShapeDialog
import com.ulrezaj.renovum_1.ui.components.list_Items.OpeningItem
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel

@Composable
fun AddRoomScreen(
	initialShapeType: RoomShapeType,
	navController: NavHostController,
	userSettings: UserSettings,
	roomViewModel: RoomViewModel,
	onSave: () -> Unit
) {
	val roomName = remember { mutableStateOf("") }
	val selectedShape = remember { mutableStateOf(initialShapeType) }
	val showShapeDialog = remember { mutableStateOf(false) }
	val showOpeningDialog = remember { mutableStateOf(false) }
	var openings by remember { mutableStateOf(listOf<OpeningEntity>()) }

	var paramValues by remember { mutableStateOf(mapOf<String, String>()) }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(rememberScrollState()),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		OutlinedButton(
			onClick = { showShapeDialog.value = true },
			modifier = Modifier.fillMaxWidth()
		) {
			Text("Обрана форма: ${selectedShape.value.getDisplayName()}")
		}

		OutlinedTextField(
			value = roomName.value,
			onValueChange = { roomName.value = it },
			label = { Text("Назва кімнати") },
			modifier = Modifier.fillMaxWidth()
		)

		HorizontalDivider()

		val fields = when (selectedShape.value) {
			RoomShapeType.RECTANGLE -> listOf("Довжина", "Ширина", "Висота")
			RoomShapeType.L_SHAPED -> listOf("A", "B", "C", "D", "Висота")
			RoomShapeType.T_SHAPED -> listOf("Ширина верху", "Висота верху", "Ширина ніжки", "Висота ніжки", "Висота")
		}

		fields.chunked(2).forEach { rowFields ->
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				rowFields.forEach { field ->
					OutlinedTextField(
						value = paramValues[field] ?: "",
						onValueChange = { newValue ->
							if (newValue.all { it.isDigit() || it == '.' }) {
								paramValues = paramValues + (field to newValue)
							}
						},
						label = { Text("$field (м)") },
						modifier = Modifier.weight(1f),
						keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
						singleLine = true
					)
				}

				if (rowFields.size == 1) {
					Spacer(modifier = Modifier.weight(1f))
				}
			}
		}

		HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = "Вікна та двері",
				style = MaterialTheme.typography.titleMedium
			)
			TextButton(onClick = { showOpeningDialog.value = true }) {
				Icon(Icons.Default.Add, contentDescription = null)
				Spacer(Modifier.width(4.dp))
				Text("Додати")
			}
		}

		val sortedOpenings = openings.sortedBy { it.type }
		sortedOpenings.forEach { opening ->
			OpeningItem(
				opening = opening,
				onDeleteClick = {
					openings = openings - opening
				}
			)
		}

		Spacer(modifier = Modifier.height(16.dp))

		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f)) {
				Text("Скасувати")
			}
			Button(
				onClick = {
					if (roomName.value.isNotBlank()) {
						val roomParams = RoomParams.fromMap(selectedShape.value, paramValues)

						val newRoom = RoomEntity(
							name = roomName.value,
							shapeType = selectedShape.value,
							params = roomParams,
							openings = openings
						)

						roomViewModel.addRoom(newRoom)
						onSave()
					}
				},
				modifier = Modifier.weight(1f)
			) {
				Text("Зберегти")
			}
		}
	}

	if (showShapeDialog.value) {
		RoomShapeDialog(
			onDismiss = { showShapeDialog.value = false },
			columns = userSettings.dialogColumns,
			onNext = { newShape ->
				selectedShape.value = newShape
				showShapeDialog.value = false
				paramValues = emptyMap()
			}
		)
	}

	if (showOpeningDialog.value) {
		AddOpeningDialog(
			onDismiss = { showOpeningDialog.value = false },
			onConfirm = { newOpening ->
				openings = openings + newOpening
				showOpeningDialog.value = false
			}
		)
	}
}