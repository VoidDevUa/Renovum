package com.void_dev_ua.renovum.ui.screens.done_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExportTableTypeSelector(
	isGroupedByRooms: Boolean,
	onTypeSelected: (Boolean) -> Unit
) {
	SingleChoiceSegmentedButtonRow(
		modifier = Modifier
			.fillMaxWidth()
	) {
		SegmentedButton(
			shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
			onClick = { onTypeSelected(true) },
			selected = isGroupedByRooms,
			label = { Text("Покімнатий") },
			colors = SegmentedButtonDefaults.colors(
				activeContainerColor = MaterialTheme.colorScheme.tertiary,
				activeContentColor = MaterialTheme.colorScheme.onTertiary,
				inactiveContentColor = MaterialTheme.colorScheme.onSurface,
				inactiveBorderColor = MaterialTheme.colorScheme.outline
			)
		)
		SegmentedButton(
			shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
			onClick = { onTypeSelected(false) },
			selected = !isGroupedByRooms,
			label = { Text("Загальний") },
			colors = SegmentedButtonDefaults.colors(
				activeContainerColor = MaterialTheme.colorScheme.tertiary,
				activeContentColor = MaterialTheme.colorScheme.onTertiary,
				inactiveContentColor = MaterialTheme.colorScheme.onSurface,
				inactiveBorderColor = MaterialTheme.colorScheme.outline
			)
		)
	}
}