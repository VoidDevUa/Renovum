package com.ulrezaj.renovum_1.ui.components.topAppBar.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ulrezaj.renovum_1.utility.L

@Composable
fun EditModeIcon(
	isEditMode: Boolean,
	onEditClick: () -> Unit
) {
	IconButton(onClick = {
		val targetMode = if (isEditMode) "VIEW" else "EDIT"
		L.click("TopBar: Switch to $targetMode mode")
		onEditClick()
	}) {
		Icon(
			imageVector = if (isEditMode) Icons.Default.Check else Icons.Default.Edit,
			contentDescription = "Режим редагування",
			tint = if (isEditMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
		)
	}
}