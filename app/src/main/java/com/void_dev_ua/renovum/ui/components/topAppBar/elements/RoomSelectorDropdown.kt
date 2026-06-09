package com.void_dev_ua.renovum.ui.components.topAppBar.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.data.model.RoomEntity
import com.void_dev_ua.renovum.utility.L

@Composable
fun RoomSelectorDropdown(
	selectedRoom: RoomEntity?,
	rooms: List<RoomEntity>,
	onRoomSelected: (RoomEntity) -> Unit,
	modifier: Modifier = Modifier,
	isOutlined: Boolean = false
) {
	var expanded by remember { mutableStateOf(false) }
	val isEnabled = rooms.isNotEmpty()

	Box(modifier = modifier) {
		if (isOutlined) {
			Button(
				onClick = { expanded = true },
				enabled = isEnabled,
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.secondaryContainer,
					contentColor = MaterialTheme.colorScheme.onSecondaryContainer
				)
			) {
				Text(selectedRoom?.name ?: "Кімнати відсутні")
				Icon(Icons.Default.ArrowDropDown, contentDescription = null)
			}
		} else {
			TextButton(
				onClick = { expanded = true },
				enabled = isEnabled,
				contentPadding = PaddingValues(horizontal = 8.dp)
			) {
				Text(
					text = selectedRoom?.name ?: "Кімнати відсутні",
					style = MaterialTheme.typography.bodyMedium,
					maxLines = 1
				)
				Icon(Icons.Default.ArrowDropDown, contentDescription = null)
			}
		}

		DropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false }
		) {
			rooms.forEach { room ->
				DropdownMenuItem(
					text = { Text(room.name) },
					onClick = {
						L.click("TopBar: Room selected -> ${room.name}")
						onRoomSelected(room)
						expanded = false
					}
				)
			}
		}
	}
}