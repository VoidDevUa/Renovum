package com.ulrezaj.renovum_1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ulrezaj.renovum_1.data.model.RoomParams
import com.ulrezaj.renovum_1.data.model.RoomShapeType
import com.ulrezaj.renovum_1.ui.components.RoomSchemaPainter
import com.ulrezaj.renovum_1.ui.components.dialogs.AddOpeningDialog
import com.ulrezaj.renovum_1.ui.components.list_Items.OpeningItem
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L

@Composable
fun EditRoomScreen(
	roomId: String,
	navController: NavHostController,
	roomViewModel: RoomViewModel,
	onSave: () -> Unit
) {
	val room = remember(roomId) { roomViewModel.rooms.find { it.id == roomId } }

	if (room == null) {
		L.e("EditRoom: Room with ID $roomId not found")
		navController.popBackStack()
		return
	}

	val roomName = remember { mutableStateOf(room.name) }
	val focusedField = remember { mutableStateOf<String?>(null) }
	val showOpeningDialog = remember { mutableStateOf(false) }

	var openings by remember { mutableStateOf(room.openings) }

	var paramValues by remember {
		mutableStateOf(
			room.params.toMap().mapValues { it.value.toString() } + ("Висота" to room.params.roomHeight.toString())
		)
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(rememberScrollState()),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Card(
			modifier = Modifier.fillMaxWidth(),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
		) {
			Text(
				text = "Форма: ${room.shapeType.getDisplayName()}",
				modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally),
				style = MaterialTheme.typography.titleMedium,
				fontWeight = FontWeight.Bold
			)
		}

		val painterParams = paramValues.mapValues { it.value.toDoubleOrNull() ?: 0.0 }

		RoomSchemaPainter(
			shapeType = room.shapeType,
			focusedField = focusedField.value,
			paramValues = painterParams,
			modifier = Modifier.fillMaxWidth().height(220.dp)
		)

		OutlinedTextField(
			value = roomName.value,
			onValueChange = { roomName.value = it },
			label = { Text("Назва кімнати") },
			modifier = Modifier.fillMaxWidth()
		)

		HorizontalDivider()

		val fields = when (room.shapeType) {
			RoomShapeType.RECTANGLE -> listOf("Довжина", "Ширина", "Висота")
			RoomShapeType.L_SHAPED -> listOf("A", "B", "C", "D", "Висота")
			RoomShapeType.T_SHAPED -> listOf(
				"Ліве плече", "Праве плече", "Висота верху (ліво)",
				"Ширина ніжки", "Ліва ніжка", "Права ніжка", "Висота"
			)
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
						modifier = Modifier
							.weight(1f)
							.onFocusChanged { focusState ->
								if (focusState.isFocused) focusedField.value = field
								else if (focusedField.value == field) focusedField.value = null
							},
						keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
						singleLine = true
					)
				}
				if (rowFields.size == 1) Spacer(modifier = Modifier.weight(1f))
			}
		}

		HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(text = "Вікна та двері", style = MaterialTheme.typography.titleMedium)
			TextButton(onClick = { showOpeningDialog.value = true }) {
				Icon(Icons.Default.Add, contentDescription = null)
				Spacer(Modifier.width(4.dp))
				Text("Додати")
			}
		}

		openings.sortedBy { it.type }.forEach { opening ->
			OpeningItem(
				opening = opening,
				onDeleteClick = { openings = openings - opening }
			)
		}

		Spacer(modifier = Modifier.height(16.dp))

		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			OutlinedButton(
				onClick = { navController.popBackStack() },
				modifier = Modifier.weight(1f)
			) {
				Text("Скасувати")
			}
			Button(
				onClick = {
					if (roomName.value.isNotBlank()) {
						val updatedParams = RoomParams.fromMap(room.shapeType, paramValues)
						val updatedRoom = room.copy(
							name = roomName.value,
							params = updatedParams,
							openings = openings
						)

						roomViewModel.deleteRoom(room)
						roomViewModel.addRoom(updatedRoom)

						L.d("EditRoom: Room updated: ${updatedRoom.name}")
						onSave()
					}
				},
				modifier = Modifier.weight(1f)
			) {
				Text("Оновити")
			}
		}
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