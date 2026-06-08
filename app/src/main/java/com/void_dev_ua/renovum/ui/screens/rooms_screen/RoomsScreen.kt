package com.void_dev_ua.renovum.ui.screens.rooms_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.data.model.OpeningType
import com.void_dev_ua.renovum.data.model.RoomEntity
import com.void_dev_ua.renovum.ui.screens.rooms_screen.components.RoomCard
import com.void_dev_ua.renovum.ui.screens.rooms_screen.components.NewProjectDialog
import com.void_dev_ua.renovum.utility.L

@Composable
fun RoomsScreen(
	rooms: List<RoomEntity>,
	userSettings: UserSettings,
	isEditMode: Boolean,
	onAddRoomClick: () -> Unit,
	onRoomClick: (RoomEntity) -> Unit,
	onDeleteRoom: (RoomEntity) -> Unit,
	onSettingsChange: (UserSettings) -> Unit
) {
	val roomToDelete = remember { mutableStateOf<RoomEntity?>(null) }
	val showNewProjectDialog = remember { mutableStateOf(false) }

	roomToDelete.value?.let { room ->
		AlertDialog(
			onDismissRequest = { roomToDelete.value = null },
			title = { Text("Видалення кімнати") },
			text = { Text("Ви впевнені, що хочете видалити «${room.name}»? Всі внесені виміри та виконані роботи в цій кімнаті будуть безповоротно втрачені.") },
			confirmButton = {
				TextButton(
					onClick = {
						onDeleteRoom(room)
						roomToDelete.value = null
					}
				) {
					Text("Видалити", color = MaterialTheme.colorScheme.error)
				}
			},
			dismissButton = {
				TextButton(onClick = { roomToDelete.value = null }) {
					Text("Скасувати")
				}
			}
		)
	}
	if (showNewProjectDialog.value) {
		NewProjectDialog(
			onDismiss = { showNewProjectDialog.value = false },
			onConfirm = { inputAddress ->
				onSettingsChange(userSettings.copy(currentObjectAddress = inputAddress))
				showNewProjectDialog.value = false
			}
		)
	}

	Scaffold(
		contentWindowInsets = WindowInsets(0, 0, 0, 0),
		floatingActionButtonPosition = if (userSettings.isLeftHanded) FabPosition.Start else FabPosition.End,
		floatingActionButton = {
			if (userSettings.currentObjectAddress.isNotBlank()) {
				FloatingActionButton(
					onClick = {
						L.click("FAB: Add Room")
						onAddRoomClick()
					},
					containerColor = Color(0xFF2E7D32),
					contentColor = Color.White,
					shape = MaterialTheme.shapes.large,
				) {
					Icon(
						imageVector = Icons.Default.Add,
						contentDescription = "Додати кімнату",
						modifier = Modifier.size(30.dp)
					)
				}
			}
		}
	) { paddingValues ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			Box(modifier = Modifier.fillMaxSize()) {
				if (userSettings.currentObjectAddress.isBlank()) {
					// Стан 1: Ремонт ще не розпочато (немає адреси)
					Column(
						modifier = Modifier
							.fillMaxSize()
							.padding(32.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(
							text = "Немає активного ремонту",
							style = MaterialTheme.typography.headlineSmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							textAlign = TextAlign.Center
						)
						Spacer(modifier = Modifier.height(8.dp))
						Text(
							text = "Введіть адресу, щоб розпочати новий кошторис.",
							style = MaterialTheme.typography.bodyMedium,
							color = MaterialTheme.colorScheme.outline,
							textAlign = TextAlign.Center
						)
						Spacer(modifier = Modifier.height(24.dp))
						Button(
							onClick = { showNewProjectDialog.value = true },
							colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
						) {
							Text("Почати новий ремонт", color = Color.White)
						}
					}
				} else if (rooms.isEmpty()) {
					// Стан 2: Адреса є, але кімнат ще не додали
					Column(
						modifier = Modifier
							.fillMaxSize()
							.padding(32.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(
							text = "Об'єкт створено успішно!",
							style = MaterialTheme.typography.headlineSmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							textAlign = TextAlign.Center
						)
						Spacer(modifier = Modifier.height(8.dp))
						Text(
							text = "Тепер натисніть на кнопку '+' знизу, щоб додати першу кімнату.",
							style = MaterialTheme.typography.bodyMedium,
							color = MaterialTheme.colorScheme.outline,
							textAlign = TextAlign.Center
						)
					}
				} else {
					// Список кімнат
					LazyColumn(
						modifier = Modifier.fillMaxSize(),
						contentPadding = PaddingValues(vertical = 8.dp)
					) {
						items(rooms) { room ->
							val windowsCount = room.openings.count { it.type == OpeningType.WINDOW }
							val doorsCount = room.openings.count { it.type == OpeningType.DOOR }

							RoomCard(
								name = room.name,
								shapeType = room.shapeType,
								dimensions = "Вікон: $windowsCount, Дверей: $doorsCount",
								showDimensions = userSettings.showDimensionsInCard,
								isEditMode = isEditMode,
								isLeftHanded = userSettings.isLeftHanded,
								onDeleteClick = {
									L.click("RoomCard Request Delete: ${room.name}")
									roomToDelete.value = room
								},
								onClick = {
									L.click("RoomCard Open: ${room.name} (id: ${room.id})")
									onRoomClick(room)
								}
							)
						}
					}
				}
			}
		}
	}
}