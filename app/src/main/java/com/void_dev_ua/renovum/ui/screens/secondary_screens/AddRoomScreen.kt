package com.void_dev_ua.renovum.ui.screens.secondary_screens

import android.widget.Toast
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.model.OpeningEntity
import com.void_dev_ua.renovum.model.RoomEntity
import com.void_dev_ua.renovum.model.RoomParams
import com.void_dev_ua.renovum.model.RoomShapeType
import com.void_dev_ua.renovum.ui.components.dialogs.AddOpeningDialog
import com.void_dev_ua.renovum.ui.components.dialogs.RoomShapeDialog
import com.void_dev_ua.renovum.ui.components.list_Items.OpeningItem
import com.void_dev_ua.renovum.ui.components.RoomSchemaPainter
import com.void_dev_ua.renovum.viewmodel.RoomViewModel
import com.void_dev_ua.renovum.utility.L

@Composable
fun AddRoomScreen(
	initialShapeType: RoomShapeType,
	navController: NavHostController,
	userSettings: UserSettings,
	roomViewModel: RoomViewModel,
	onSave: () -> Unit
) {
	val context = LocalContext.current

	val roomName = remember { mutableStateOf("") }
	val selectedShape = remember { mutableStateOf(initialShapeType) }
	val showShapeDialog = remember { mutableStateOf(false) }
	val showOpeningDialog = remember { mutableStateOf(false) }
	val focusedField = remember { mutableStateOf<String?>(null) }

	var openings by remember { mutableStateOf(listOf<OpeningEntity>()) }
	var paramValues by remember { mutableStateOf(mapOf("Висота" to "2.5")) }
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(rememberScrollState()),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	)
	{
		OutlinedButton(
			onClick = {
				L.click("AddRoom: Open Shape Dialog")
				showShapeDialog.value = true
				},
			modifier = Modifier.fillMaxWidth()
		) {
			Text("Обрана форма: ${selectedShape.value.getDisplayName()}")
		}

		val painterParams = paramValues.mapValues { it.value.toDoubleOrNull() ?: 0.0 }

		RoomSchemaPainter(
			shapeType = selectedShape.value,
			focusedField = focusedField.value,
			paramValues = painterParams,
			modifier = Modifier
				.fillMaxWidth()
				.height(220.dp)
		)

		OutlinedTextField(
			value = roomName.value,
			onValueChange = { if (it.length <= 25) roomName.value = it },
			label = { Text("Назва кімнати") },
			modifier = Modifier.fillMaxWidth(),
			singleLine = true
		)

		HorizontalDivider()

		val fields = when (selectedShape.value) {
			RoomShapeType.RECTANGLE -> listOf(
				"Довжина", "Ширина", "Висота"
			)
			RoomShapeType.L_SHAPED -> listOf(
				"A", "B", "C", "D", "Висота"
			)
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
							if (newValue.length <= 5 && newValue.all { it.isDigit() || it == '.' }) {
								paramValues = paramValues + (field to newValue)
							}
						},
						label = { Text("$field (м)") },
						modifier = Modifier
							.weight(1f)
							.onFocusChanged { focusState ->
								if (focusState.isFocused) {
									L.d("AddRoom: Focus on field $field")
									focusedField.value = field
								} else if (focusedField.value == field) {
									focusedField.value = null
								}
							},
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
			TextButton(onClick = {
				L.click("AddRoom: Open Add Opening Dialog")
				showOpeningDialog.value = true
			})
			{
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
					L.click("AddRoom: Remove opening ${opening.type}")
					openings = openings - opening
				}
			)
		}

		Spacer(modifier = Modifier.height(16.dp))

		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			OutlinedButton(onClick = {
				L.click("AddRoom: Cancel")
				navController.popBackStack()
			}, modifier = Modifier.weight(1f)) {
				Text("Скасувати")
			}
			Button(
				onClick = {
					L.click("AddRoom: Save clicked")

					val currentHeight = paramValues["Висота"]?.toDoubleOrNull() ?: 2.5
					val hasInvalidOpenings = openings.any { (it.height.toDoubleOrNull() ?: 0.0) > currentHeight }

					if (roomName.value.isBlank()) {
						L.e("AddRoom: Save failed - Room name is blank")
						Toast.makeText(context, "Введіть назву кімнати", Toast.LENGTH_SHORT).show()
					}
					else if (currentHeight < 2.5) {
						L.e("AddRoom: Save failed - Room height $currentHeight is below DBN limit (2.5m)")
						Toast.makeText(context, "Мінімальна висота житлового приміщення за ДБН — 2.5 м!", Toast.LENGTH_LONG).show()
					}
					else if (hasInvalidOpenings) {
						L.e("AddRoom: Save failed - Opening height exceeds room height")
						Toast.makeText(context, "Висота прорізу(-ів) більша за висоту кімнати!", Toast.LENGTH_SHORT).show()
					}
					else {
						val roomParams = RoomParams.fromMap(selectedShape.value, paramValues)

						L.d("AddRoom: Creating room ${roomName.value} with shape ${selectedShape.value}")
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
				L.click("AddRoom: Shape changed to $newShape")
				selectedShape.value = newShape
				showShapeDialog.value = false
				paramValues = emptyMap()
			}
		)
	}

	if (showOpeningDialog.value) {
		val currentCeilingHeight = paramValues["Висота"]?.toDoubleOrNull() ?: 2.5
		AddOpeningDialog(
			maxHeight = currentCeilingHeight,
			onDismiss = { showOpeningDialog.value = false },
			onConfirm = { newOpening ->
				L.d("AddRoom: Opening added: ${newOpening.type} (${newOpening.width}x${newOpening.height})")
				openings = openings + newOpening
				showOpeningDialog.value = false
			}
		)
	}
}