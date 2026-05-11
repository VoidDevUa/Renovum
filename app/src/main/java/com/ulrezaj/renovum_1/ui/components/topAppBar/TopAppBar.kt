package com.ulrezaj.renovum_1.ui.components.topAppBar

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.ui.components.topAppBar.elements.RoomSelectorDropdown
import com.ulrezaj.renovum_1.ui.components.topAppBar.elements.EditModeIcon
import com.ulrezaj.renovum_1.ui.components.topAppBar.elements.NavigateToEditRoomIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovumTopAppBar(
	currentScreenTitle: String,
	isLeftHanded: Boolean,
	isEditMode: Boolean = false,
	onEditClick: (() -> Unit)? = null,
	onNavigateToEdit: (() -> Unit)? = null,
	selectedRoom: RoomEntity? = null,
	rooms: List<RoomEntity> = emptyList(),
	onRoomSelected: ((RoomEntity) -> Unit)? = null
) {
	val content = @Composable {
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

	CenterAlignedTopAppBar(
		title = { Text(currentScreenTitle, style = MaterialTheme.typography.titleLarge) },
		navigationIcon = { if (isLeftHanded) content() },
		actions = { if (!isLeftHanded) content() }
	)
}