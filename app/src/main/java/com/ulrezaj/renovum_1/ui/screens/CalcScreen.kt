package com.ulrezaj.renovum_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.ui.components.RoomSchemaPainter
import com.ulrezaj.renovum_1.utility.L

@Composable
fun CalcScreen(
	currentRoom: RoomEntity,
	allRooms: List<RoomEntity>,
	userSettings: UserSettings,
	onRoomSelected: (RoomEntity) -> Unit
) {
	LaunchedEffect(currentRoom.id) {
		L.d("CalcScreen: Active room is ${currentRoom.name} (ID: ${currentRoom.id})")
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(rememberScrollState())
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			val selector = @Composable {
				RoomDropdownSelector(
					currentRoom = currentRoom,
					allRooms = allRooms,
					onRoomSelected = onRoomSelected
				)
			}
			val title = @Composable {
				Text(text = "Форма: ${currentRoom.shapeType.getDisplayName()}")
			}

			if (userSettings.isLeftHanded) {
				selector()
				title()
			} else {
				title()
				selector()
			}
		}

		RoomSchemaPainter(
			shapeType = currentRoom.shapeType,
			focusedField = null,
			paramValues = currentRoom.params.toMap(),
			modifier = Modifier.height(250.dp)
		)

		// Далі блоки з розрахунками
	}
}

@Composable
fun RoomDropdownSelector(
	currentRoom: RoomEntity,
	allRooms: List<RoomEntity>,
	onRoomSelected: (RoomEntity) -> Unit
) {
	var expanded by remember { mutableStateOf(false) }

	Box {
		Button(onClick = {
			L.click("Room Selector Dropdown Opened")
			expanded = true
		}) {
			Text(text = currentRoom.name)
			Icon(Icons.Default.ArrowDropDown, contentDescription = null)
		}
		DropdownMenu(
			expanded = expanded,
			onDismissRequest = {
				expanded = false
			}
		) {
			allRooms.forEach { room ->
				DropdownMenuItem(
					text = { Text(room.name) },
					onClick = {
						L.click("Dropdown: Selected room ${room.name}")
						onRoomSelected(room)
						expanded = false
					}
				)
			}
		}
	}
}