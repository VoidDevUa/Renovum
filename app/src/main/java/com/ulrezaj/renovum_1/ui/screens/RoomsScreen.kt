package com.ulrezaj.renovum_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.OpeningType
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.ui.components.list_Items.RoomCard
import com.ulrezaj.renovum_1.utility.L

@Composable
fun RoomsScreen(
	rooms: List<RoomEntity>,
	userSettings: UserSettings,
	isEditMode: Boolean,
	onAddRoomClick: () -> Unit,
	onRoomClick: (RoomEntity) -> Unit,
	onDeleteRoom: (RoomEntity) -> Unit
) {
	LaunchedEffect(rooms.size) {
		L.d("RoomsScreen: Displaying ${rooms.size} rooms")
	}

	Scaffold(
		contentWindowInsets = WindowInsets(0, 0, 0, 0),
		floatingActionButton = {
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
	) { paddingValues ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			if (rooms.isEmpty()) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(32.dp),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Text(
						text = "Кімнат ще немає",
						style = MaterialTheme.typography.headlineSmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						textAlign = TextAlign.Center
					)
				}
			} else {
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
							onDeleteClick = {
								L.click("RoomCard Delete: ${room.name}")
								onDeleteRoom(room)
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