package com.void_dev_ua.renovum.ui.components.topAppBar.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.void_dev_ua.renovum.utility.L

@Composable
fun NavigateToEditRoomIcon(
	onNavigate: () -> Unit
) {
	IconButton(onClick = {
		L.click("TopBar: Navigate to Edit Room Screen")
		onNavigate()
	}) {
		Icon(
			imageVector = Icons.Default.Edit,
			contentDescription = "На сторінку редагування"
		)
	}
}