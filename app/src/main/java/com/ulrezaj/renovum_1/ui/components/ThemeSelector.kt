package com.ulrezaj.renovum_1.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelector(
	selectedTheme: AppTheme,
	onThemeSelected: (AppTheme) -> Unit
) {
	val options = listOf(
		AppTheme.Light to "Світла",
		AppTheme.Dark to "Темна",
		AppTheme.System to "Системна"
	)

	SingleChoiceSegmentedButtonRow(
		modifier = Modifier
			.fillMaxWidth()
	) {
		options.forEachIndexed { index, (theme, label) ->
			SegmentedButton(
				shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
				onClick = { onThemeSelected(theme) },
				selected = selectedTheme == theme,
				label = { Text(label) },
				colors = SegmentedButtonDefaults.colors(
					activeContainerColor = MaterialTheme.colorScheme.tertiary,
					activeContentColor = MaterialTheme.colorScheme.onTertiary,
					inactiveContentColor = MaterialTheme.colorScheme.onSurface,
					inactiveBorderColor = MaterialTheme.colorScheme.outline
				)
			)
		}
	}
}