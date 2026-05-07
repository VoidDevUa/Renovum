package com.ulrezaj.renovum_1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.ui.theme.AppTheme
import com.ulrezaj.renovum_1.ui.components.ThemeSelector

@Composable
fun SettingsScreen(
	settings: UserSettings,
	onSettingsChange: (UserSettings) -> Unit
) {
	Column(modifier = Modifier.padding(16.dp)) {
		Text(
			"Зовнішній вигляд",
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.primary
		)
		Spacer(modifier = Modifier.height(8.dp))

		Text("Тема додатка", style = MaterialTheme.typography.bodyLarge)
		Spacer(modifier = Modifier.height(8.dp))

		ThemeSelector(
			selectedTheme = settings.appTheme,
			onThemeSelected = { newTheme ->
				onSettingsChange(settings.copy(appTheme = newTheme))
			}
		)

		Spacer(modifier = Modifier.height(16.dp))

		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth()
		) {
			Text("Показувати розміри на картках", modifier = Modifier.weight(1f))
			Switch(
				checked = settings.showDimensionsInCard,
				onCheckedChange = { isChecked ->
					onSettingsChange(settings.copy(showDimensionsInCard = isChecked))
				}
			)
		}

		Spacer(modifier = Modifier.height(24.dp))
		HorizontalDivider()
		Spacer(modifier = Modifier.height(16.dp))

		Text(
			"Налаштування керування",
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.primary
		)

		// Режим лівші
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp)
		) {
			Text("Режим лівші", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
			Switch(
				checked = settings.isLeftHanded,
				onCheckedChange = { isChecked ->
					onSettingsChange(settings.copy(isLeftHanded = isChecked))
				}
			)
		}

		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth()
		) {
			Text("Стовпців у виборі форми (2 або 3)", modifier = Modifier.weight(1f))
			Switch(
				checked = settings.dialogColumns == 3,
				onCheckedChange = { isThree ->
					onSettingsChange(settings.copy(dialogColumns = if (isThree) 3 else 2))
				}
			)
		}
	}
}