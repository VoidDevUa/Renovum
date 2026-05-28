package com.ulrezaj.renovum_1.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
	navController: NavHostController,
	roomViewModel: RoomViewModel,
	onSave: () -> Unit
) {
	val context = LocalContext.current

	val room = roomViewModel.selectedRoom

	if (room == null) {
		L.e("EditRoom: selectedRoom is null, cannot edit")
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
				onClick = {
					L.click("EditRoom: Cancel")
					navController.popBackStack()
				},
				modifier = Modifier.weight(1f)
			) {
				Text("Скасувати")
			}
			Button(
				onClick = {
					L.click("EditRoom: Update clicked")

					val currentHeight = paramValues["Висота"]?.toDoubleOrNull() ?: 0.0

					val hasInvalidOpenings = openings.any { (it.height.toDoubleOrNull() ?: 0.0) > currentHeight }

					if (roomName.value.isBlank()) {
						L.e("EditRoom: Update failed - Room name is blank")
						Toast.makeText(context, "Введіть назву кімнати", Toast.LENGTH_SHORT).show()
					}
					else if (hasInvalidOpenings) {
						L.e("EditRoom: Update failed - Opening height exceeds room height")
						Toast.makeText(context, "Висота прорізу(-ів) більша за висоту кімнати!", Toast.LENGTH_LONG).show()
					}
					else {
						val updatedParams = RoomParams.fromMap(room.shapeType, paramValues)
						val updatedRoom = room.copy(
							name = roomName.value,
							params = updatedParams,
							openings = openings
						)

						L.d("EditRoom: Updating room ID ${room.id}. Old name: ${room.name}, New name: ${updatedRoom.name}")
						roomViewModel.addRoom(updatedRoom)
						roomViewModel.selectRoom(updatedRoom)

						L.d("EditRoom: Room updated successfully")
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
		val currentCeilingHeight = paramValues["Висота"]?.toDoubleOrNull() ?: 3.0

		AddOpeningDialog(
			maxHeight = currentCeilingHeight,
			onDismiss = { showOpeningDialog.value = false },
			onConfirm = { newOpening ->
				L.d("EditRoom: Opening added: ${newOpening.type} (${newOpening.width}x${newOpening.height})")
				openings = openings + newOpening
				showOpeningDialog.value = false
			}
		)
	}
}