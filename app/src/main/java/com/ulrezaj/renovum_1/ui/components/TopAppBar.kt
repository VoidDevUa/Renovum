package com.ulrezaj.renovum_1.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ulrezaj.renovum_1.utility.L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovumTopAppBar(
	currentScreenTitle: String,
	isLeftHanded: Boolean,
	isEditMode: Boolean = false,
	onEditClick: (() -> Unit)? = null,
	onNavigateToEdit: (() -> Unit)? = null
) {
	val editIcon = @Composable {
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

	val navigateToEditIcon = @Composable {
		if (onNavigateToEdit != null) {
			IconButton(onClick = {
				L.click("TopBar: Navigate to Edit Room Screen")
				onNavigateToEdit()
			}) {
				Icon(
					imageVector = Icons.Default.Edit,
					contentDescription = "На сторінку редагування"
				)
			}
		}
	}

	CenterAlignedTopAppBar(
		title = { Text(currentScreenTitle, style = MaterialTheme.typography.titleLarge) },
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.surface,
			scrolledContainerColor = Color.Unspecified,
			navigationIconContentColor = Color.Unspecified,
			titleContentColor = Color.Unspecified,
			actionIconContentColor = Color.Unspecified
		),
		navigationIcon = {
			if (isLeftHanded) {
				Row {
					editIcon()
					navigateToEditIcon()
				}
			}
		},
		actions = {
			if (!isLeftHanded) {
				Row {
					editIcon()
					navigateToEditIcon()
				}
			}
		}
	)
}