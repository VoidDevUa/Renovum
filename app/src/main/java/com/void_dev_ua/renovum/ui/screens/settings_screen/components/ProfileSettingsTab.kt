package com.void_dev_ua.renovum.ui.screens.settings_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.data.UserSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsTab(
	settings: UserSettings,
	onSettingsChange: (UserSettings) -> Unit
) {
	val focusManager = LocalFocusManager.current

	val nameInput = remember(settings.masterName) { mutableStateOf(settings.masterName) }
	val phoneInput = remember(settings.masterPhone) { mutableStateOf(settings.masterPhone) }

	val isEditingName = remember { mutableStateOf(false) }
	val isEditingPhone = remember { mutableStateOf(false) }

	val digitsCount = phoneInput.value.count { it.isDigit() }
	val isPhoneInvalid = phoneInput.value.isNotBlank() && digitsCount < 12

	val cities = listOf(
		"Вінниця", "Дніпро", "Житомир",
		"Запоріжжя", "Івано-Франківськ",
		"Київ", "Кропивницький", "Луцьк",
		"Львів", "Миколаїв", "Одеса",
		"Полтава", "Рівне", "Суми",
		"Тернопіль", "Ужгород", "Харків",
		"Херсон", "Хмельницький", "Черкаси",
		"Чернівці", "Чернігів")
	val expandedCitySelector = remember { mutableStateOf(false) }
	val selectedCity = remember { mutableStateOf(cities[14]) }

	/*
    // Вибір форми діяльності майстра
    val businessTypes = listOf("Приватний майстер", "ФОП", "ТОВ", "Будівельна компанія")
    val expandedTypeSelector = remember { mutableStateOf(false) }
    val selectedBusinessType = remember { mutableStateOf(businessTypes[0]) }
    */

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Text(
			"Дані для кошторису",
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.primary
		)
		Text(
			"Ці дані будуть автоматично підставлятися в шапку кожного згенерованого Word-документа.",
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)

		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(8.dp)
		) {
			if (isEditingName.value) {
				OutlinedTextField(
					value = nameInput.value,
					onValueChange = { if (it.length <= 45) nameInput.value = it },
					label = { Text("ПІБ Майстра / Назва компанії") },
					modifier = Modifier.weight(1f),
					singleLine = true
				)
			} else {
				// Заміна на звичайний Text, коли редагування вимкнено
				Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp, vertical = 16.dp)) {
					Text("ПІБ Майстра / Назва компанії", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
					Text(nameInput.value.ifBlank { "Не вказано" }, style = MaterialTheme.typography.bodyLarge)
				}
			}

			IconButton(
				onClick = {
					if (isEditingName.value) {
						onSettingsChange(settings.copy(masterName = nameInput.value.trim()))
						focusManager.clearFocus()
					}
					isEditingName.value = !isEditingName.value
				}
			) {
				Icon(
					imageVector = if (isEditingName.value) Icons.Default.Check else Icons.Default.Edit,
					contentDescription = null
				)
			}
		}

		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(8.dp)
		) {
			if (isEditingPhone.value) {
				OutlinedTextField(
					value = phoneInput.value,
					onValueChange = { if (it.length <= 13) phoneInput.value = it },
					label = { Text("Номер телефону") },
					placeholder = { Text("+38") },
					isError = isPhoneInvalid,
					modifier = Modifier.weight(1f).onFocusChanged {
						if (it.isFocused && phoneInput.value.isBlank()) phoneInput.value = "+38"
					},
					singleLine = true
				)
			} else {
				Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp, vertical = 16.dp)) {
					Text("Номер телефону", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
					Text(phoneInput.value.ifBlank { "Не вказано" }, style = MaterialTheme.typography.bodyLarge)
				}
			}

			IconButton(
				onClick = {
					if (isEditingPhone.value) {
						val finalDigits = phoneInput.value.count { it.isDigit() }
						if (finalDigits >= 12) {
							onSettingsChange(settings.copy(masterPhone = phoneInput.value.trim()))
						} else {
							onSettingsChange(settings.copy(masterPhone = ""))
							phoneInput.value = ""
						}
						focusManager.clearFocus()
					}
					isEditingPhone.value = !isEditingPhone.value
				}
			) {
				Icon(
					imageVector = if (isEditingPhone.value) Icons.Default.Check else Icons.Default.Edit,
					contentDescription = null
				)
			}
		}

		/*
       // Рендеринг вибору форми діяльності
       Text(
          "Тип діяльності",
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier.padding(top = 8.dp)
       )

       ExposedDropdownMenuBox(
          expanded = expandedTypeSelector.value,
          onExpandedChange = { expandedTypeSelector.value = !expandedTypeSelector.value },
          modifier = Modifier.fillMaxWidth()
       ) {
          OutlinedTextField(
             modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true),
             readOnly = true,
             value = selectedBusinessType.value,
             onValueChange = {},
             label = { Text("Юридичний статус майстра") },
             trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTypeSelector.value) },
             colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
          )
          ExposedDropdownMenu(
             expanded = expandedTypeSelector.value,
             onDismissRequest = { expandedTypeSelector.value = false }
          ) {
             businessTypes.forEach { type ->
                DropdownMenuItem(
                   text = { Text(type) },
                   onClick = {
                      selectedBusinessType.value = type
                      expandedTypeSelector.value = false
                      // onSettingsChange(settings.copy(businessType = type))
                   },
                   contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
             }
          }
       }
       */

		Text(
			"Регіональні налаштування",
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.primary,
			modifier = Modifier.padding(top = 8.dp)
		)

		ExposedDropdownMenuBox(
			expanded = expandedCitySelector.value,
			onExpandedChange = { expandedCitySelector.value = !expandedCitySelector.value },
			modifier = Modifier.fillMaxWidth()
		) {
			OutlinedTextField(
				modifier = Modifier
					.fillMaxWidth()
					.menuAnchor(
						type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
						enabled = true
					),
				readOnly = true,
				value = selectedCity.value,
				onValueChange = {},
				label = { Text("Місто (для аналітики цін)") },
				trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCitySelector.value) },
				colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
			)
			ExposedDropdownMenu(
				expanded = expandedCitySelector.value,
				onDismissRequest = { expandedCitySelector.value = false }
			) {
				cities.forEach { city ->
					DropdownMenuItem(
						text = { Text(city) },
						onClick = {
							selectedCity.value = city
							expandedCitySelector.value = false
							//TODO: onSettingsChange(settings.copy(city = city))
						},
						contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
					)
				}
			}
		}
	}
}