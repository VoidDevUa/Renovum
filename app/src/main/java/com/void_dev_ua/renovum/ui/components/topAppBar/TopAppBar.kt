package com.void_dev_ua.renovum.ui.components.topAppBar

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.void_dev_ua.renovum.data.model.RoomEntity
import com.void_dev_ua.renovum.ui.components.topAppBar.elements.RoomSelectorDropdown
import com.void_dev_ua.renovum.ui.components.topAppBar.elements.EditModeIcon
import com.void_dev_ua.renovum.ui.components.topAppBar.elements.NavigateToEditRoomIcon
import com.void_dev_ua.renovum.ui.components.topAppBar.elements.TotalSumDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovumTopAppBar(
	currentScreenTitle: String,
	isLeftHanded: Boolean,
	totalSum: Double? = null,
	onSumClick: (() -> Unit)? = null,
	isEditMode: Boolean = false,
	onEditClick: (() -> Unit)? = null,
	onNavigateToEdit: (() -> Unit)? = null,
	selectedRoom: RoomEntity? = null,
	rooms: List<RoomEntity> = emptyList(),
	onRoomSelected: ((RoomEntity) -> Unit)? = null
) {
	val controlsContent = @Composable {
		Row(verticalAlignment = Alignment.CenterVertically) {
			onEditClick?.let { onClick ->
				EditModeIcon(isEditMode = isEditMode, onEditClick = onClick)
			}

			onNavigateToEdit?.let { onClick ->
				NavigateToEditRoomIcon(onNavigate = onClick)
			}

			onRoomSelected?.let { onSelect ->
				RoomSelectorDropdown(
					selectedRoom = selectedRoom,
					rooms = rooms,
					onRoomSelected = onSelect
				)
			}
		}
	}

	val sumContent = @Composable {
		onSumClick?.let { onClick ->
			totalSum?.let { sum ->
				TotalSumDisplay(totalSum = sum, onClick = onClick)
			}
		}
	}

	CenterAlignedTopAppBar(
		title = { Text(currentScreenTitle, style = MaterialTheme.typography.titleLarge) },
		navigationIcon = {
			if (isLeftHanded) controlsContent() else sumContent()
		},
		actions = {
			if (isLeftHanded) sumContent() else controlsContent()
		}
	)
}