package com.ulrezaj.renovum_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.data.model.WorkUnit
import com.ulrezaj.renovum_1.ui.components.dialogs.AddWorkDialog
import com.ulrezaj.renovum_1.ui.components.list_Items.WorkCard
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorksScreen(
	roomViewModel: RoomViewModel,
	userSettings: UserSettings
) {
	val rooms = roomViewModel.rooms
	var selectedRoom by remember { mutableStateOf(rooms.firstOrNull()) }
	var expandedRoomDrop by remember { mutableStateOf(false) }

	val sections = remember {
		listOf(
			"ОЗДОБЛЮВАЛЬНІ РОБОТИ",
			"ІНЖЕНЕРНІ СИСТЕМИ ТА КОМУНІКАЦІЇ",
			"ЗНЕСЕННЯ І ДЕМОНТАЖ"
		)
	}
	var currentSectionIndex by remember { mutableIntStateOf(0) }

	var showAddDialog by remember { mutableStateOf(false) }
	var workToProcess by remember { mutableStateOf<WorkService?>(null) }

	var addedWorksIds by remember { mutableStateOf(setOf<String>()) }

	val textFieldColors = OutlinedTextFieldDefaults.colors(
		focusedBorderColor = MaterialTheme.colorScheme.primary, // DarkText/LightText
		unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
		focusedLabelColor = MaterialTheme.colorScheme.primary,
		unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
	)

	if (showAddDialog && workToProcess != null) {
		AddWorkDialog(
			work = workToProcess!!,
			roomName = selectedRoom?.name ?: "",
			onDismiss = { showAddDialog = false },
			onSave = { price, vol ->
				addedWorksIds = addedWorksIds + workToProcess!!.id
				showAddDialog = false
			}
		)
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 12.dp, vertical = 4.dp)
	) {
		ExposedDropdownMenuBox(
			expanded = expandedRoomDrop,
			onExpandedChange = { expandedRoomDrop = !expandedRoomDrop },
			modifier = Modifier.fillMaxWidth()
		) {
			OutlinedTextField(
				value = selectedRoom?.name ?: "Кімнат не створено",
				onValueChange = {},
				readOnly = true,
				label = { Text("Кімната") },
				trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRoomDrop) },
				modifier = Modifier
					.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
					.fillMaxWidth(),
				colors = textFieldColors,
				textStyle = MaterialTheme.typography.bodyMedium
			)

			if (rooms.isNotEmpty()) {
				ExposedDropdownMenu(
					expanded = expandedRoomDrop,
					onDismissRequest = { expandedRoomDrop = false }
				) {
					rooms.forEach { room ->
						DropdownMenuItem(
							text = { Text(room.name) },
							onClick = {
								selectedRoom = room
								expandedRoomDrop = false
							}
						)
					}
				}
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		Card(
			modifier = Modifier
				.fillMaxWidth()
				.height(48.dp),
			elevation = CardDefaults.cardElevation(2.dp),
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.surfaceVariant
			),
			border = androidx.compose.foundation.BorderStroke(
				1.dp,
				MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
			)
		) {
			Row(
				modifier = Modifier.fillMaxSize(),
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(
					onClick = { if (currentSectionIndex > 0) currentSectionIndex-- },
					enabled = currentSectionIndex > 0
				) {
					Icon(
						Icons.AutoMirrored.Filled.KeyboardArrowLeft,
						contentDescription = null,
						tint = if (currentSectionIndex > 0)
							MaterialTheme.colorScheme.primary
						else
							MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
					)
				}

				val baseStyle = MaterialTheme.typography.titleSmall
				var columnFontSize by remember(currentSectionIndex) { mutableStateOf(baseStyle.fontSize) }

				Text(
					text = sections[currentSectionIndex],
					style = baseStyle.copy(fontSize = columnFontSize),
					modifier = Modifier.weight(1f),
					textAlign = TextAlign.Center,
					maxLines = 1,
					onTextLayout = { textLayoutResult ->
						if (textLayoutResult.hasVisualOverflow) {
							columnFontSize *= 0.9f
						}
					}
				)

				IconButton(
					onClick = { if (currentSectionIndex < sections.size - 1) currentSectionIndex++ },
					enabled = currentSectionIndex < sections.size - 1
				) {
					Icon(
						Icons.AutoMirrored.Filled.KeyboardArrowRight,
						contentDescription = null,
						tint = if (currentSectionIndex < sections.size - 1)
							MaterialTheme.colorScheme.primary
						else
							MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
				}
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		val categoriesMap = remember {
			mapOf(
				"ОЗДОБЛЮВАЛЬНІ РОБОТИ" to listOf(
					"Плиточні роботи", "Малярні роботи", "Штукатурні роботи",
					"Монтаж гіпсокартону", "Поклейка шпалер", "Покриття для підлоги"
				),
				"ІНЖЕНЕРНІ СИСТЕМИ ТА КОМУНІКАЦІЇ" to listOf(
					"Електромонтажні роботи", "Сантехнічні роботи", "Системи опалення", "Тепла підлога"
				),
				"ЗНЕСЕННЯ І ДЕМОНТАЖ" to listOf(
					"Демонтаж настінних покриттів", "Демонтаж підлогових покриттів", "Демонтаж стін та перегородок"
				)
			)
		}

		val currentCategories = categoriesMap[sections[currentSectionIndex]] ?: emptyList()
		var selectedCategory by remember { mutableStateOf("") }
		var expandedCategoryDrop by remember { mutableStateOf(false) }

		LaunchedEffect(currentSectionIndex) {
			selectedCategory = currentCategories.firstOrNull() ?: ""
		}

		ExposedDropdownMenuBox(
			expanded = expandedCategoryDrop,
			onExpandedChange = { expandedCategoryDrop = !expandedCategoryDrop },
			modifier = Modifier.fillMaxWidth()
		) {
			OutlinedTextField(
				value = selectedCategory,
				onValueChange = {},
				readOnly = true,
				label = { Text("Підрозділ") },
				trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoryDrop) },
				modifier = Modifier
					.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
					.fillMaxWidth(),
				colors = textFieldColors,
				textStyle = MaterialTheme.typography.bodyMedium
			)

			ExposedDropdownMenu(
				expanded = expandedCategoryDrop,
				onDismissRequest = { expandedCategoryDrop = false }
			) {
				currentCategories.forEach { category ->
					DropdownMenuItem(
						text = { Text(category) },
						onClick = {
							selectedCategory = category
							expandedCategoryDrop = false
						}
					)
				}
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		val worksData = remember {
			mapOf(
				"Плиточні роботи" to listOf(
					WorkService(id = "1", name = "Укладання плитки 30х30", averagePrice = 450.0, unit = WorkUnit.M2, section = "", category = ""),
					WorkService(id = "2", name = "Затирка швів", averagePrice = 80.0, unit = WorkUnit.MPOG, section = "", category = "")
				),
				"Малярні роботи" to listOf(
					WorkService(id = "3", name = "Ґрунтування стін", averagePrice = 40.0, unit = WorkUnit.M2, section = "", category = ""),
					WorkService(id = "4", name = "Фарбування у 2 шари", averagePrice = 120.0, unit = WorkUnit.M2, section = "", category = "")
				)
			)
		}

		val currentWorks = worksData[selectedCategory] ?: emptyList()

		Text(
			text = "Доступні роботи",
			style = MaterialTheme.typography.labelLarge,
			modifier = Modifier.padding(bottom = 8.dp),
			color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
		)

		LazyColumn(
			modifier = Modifier.weight(1f),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			contentPadding = PaddingValues(bottom = 16.dp)
		) {
			if (currentWorks.isEmpty()) {
				item {
					Text(
						"Робіт для категорії \"$selectedCategory\" поки немає",
						style = MaterialTheme.typography.bodyMedium,
						color = Color.Gray,
						modifier = Modifier.padding(16.dp)
					)
				}
			} else {
				items(currentWorks.size) { index ->
					val work = currentWorks[index]
					val isAlreadyAdded = addedWorksIds.contains(work.id)

					WorkCard(
						work = work,
						isLeftHanded = userSettings.isLeftHanded,
						enabled = rooms.isNotEmpty() && !isAlreadyAdded,
						isDone = isAlreadyAdded,
						onAddClick = {
							workToProcess = work
							showAddDialog = true
						}
					)
				}
			}
		}
	}
}