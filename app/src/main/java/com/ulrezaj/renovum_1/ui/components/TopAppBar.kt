package com.ulrezaj.renovum_1.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.ulrezaj.renovum_1.utility.L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovumTopAppBar(
	currentScreenTitle: String,
	isEditMode: Boolean = false,
	onEditClick: (() -> Unit)? = null
) {
	TopAppBar(
		title = { Text(currentScreenTitle) },
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.surface,
			scrolledContainerColor = MaterialTheme.colorScheme.surface
		),
		actions = {
			if (onEditClick != null) {
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
		}
	)
}