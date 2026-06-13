package com.void_dev_ua.renovum.ui.screens.works_screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.model.WorkService
import com.void_dev_ua.renovum.data.repositories.WorkDataRepository
import com.void_dev_ua.renovum.ui.components.dialogs.WorkDialog
import com.void_dev_ua.renovum.ui.components.list_Items.WorkCard
import com.void_dev_ua.renovum.viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorksScreen(
	roomViewModel: RoomViewModel,
	userSettings: UserSettings
) {
	val worksStatusMap by roomViewModel.worksWithStatusState.collectAsState()
	val selectedRoom by roomViewModel.selectedRoom
	val roomsCount = roomViewModel.rooms.size

	val sections = remember { WorkDataRepository.allSections }
	var currentSectionIndex by rememberSaveable { mutableIntStateOf(0) }
	val currentSection = sections[currentSectionIndex]

	val currentCategories = remember(currentSection) {
		WorkDataRepository.getCategoriesForSection(currentSection)
	}

	var selectedCategory by remember(currentSection) {
		val saved = roomViewModel.lastSelectedCategory
		val categoryToSet = if (saved != null && saved.section == currentSection) {
			saved
		} else {
			currentCategories.firstOrNull() ?: currentCategories[0]
		}
		mutableStateOf(categoryToSet)
	}

	LaunchedEffect(selectedCategory) {
		roomViewModel.lastSelectedCategory = selectedCategory
	}

	val showAddDialog = remember { mutableStateOf(false) }
	var workToProcess by remember { mutableStateOf<WorkService?>(null) }

	val textFieldColors = OutlinedTextFieldDefaults.colors(
		focusedBorderColor = MaterialTheme.colorScheme.primary,
		unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
		focusedLabelColor = MaterialTheme.colorScheme.primary,
		unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
	)

	selectedRoom?.let { room ->
		if (showAddDialog.value && workToProcess != null) {
			WorkDialog(
				workService = workToProcess!!,
				room = room,
				roomViewModel = roomViewModel,
				appliedWork = null,
				onDismiss = { showAddDialog.value = false },
				onSave = { price, vol ->
					roomViewModel.saveAppliedWork(room, workToProcess!!, price, vol)
					showAddDialog.value = false
				}
			)
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 12.dp, vertical = 4.dp)
	) {
		if (roomsCount == 0) {
			Card(
				colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
				modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
			) {
				Text(
					"Спершу створіть кімнату на головному екрані",
					modifier = Modifier.padding(12.dp),
					style = MaterialTheme.typography.bodyMedium
				)
			}
		}

		Card(
			modifier = Modifier.fillMaxWidth().height(48.dp),
			elevation = CardDefaults.cardElevation(2.dp),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
			border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
		) {
			Row(
				modifier = Modifier.fillMaxSize(),
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(
					onClick = { if (currentSectionIndex > 0) currentSectionIndex-- },
					enabled = currentSectionIndex > 0
				)
				{
					Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null,
						tint = if (currentSectionIndex > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(0.5f))
				}

				Text(
					text = currentSection.displayName,
					style = MaterialTheme.typography.titleSmall,
					modifier = Modifier.weight(1f),
					textAlign = TextAlign.Center,
					maxLines = 1
				)

				IconButton(
					onClick = { if (currentSectionIndex < sections.size - 1) currentSectionIndex++ },
					enabled = currentSectionIndex < sections.size - 1
				) {
					Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null,
						tint = if (currentSectionIndex < sections.size - 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(0.5f))
				}
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		var expandedCategoryDrop by remember { mutableStateOf(false) }

		ExposedDropdownMenuBox(
			expanded = expandedCategoryDrop,
			onExpandedChange = { expandedCategoryDrop = !expandedCategoryDrop },
			modifier = Modifier.fillMaxWidth()
		) {
			OutlinedTextField(
				value = selectedCategory.displayName,
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
						text = { Text(category.displayName) },
						onClick = {
							selectedCategory = category
							expandedCategoryDrop = false
						}
					)
				}
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		val currentWorks = remember(selectedCategory) {
			WorkDataRepository.getWorksForCategory(selectedCategory)
		}

		Text(
			text = "Доступні роботи",
			style = MaterialTheme.typography.labelLarge,
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
						"Робіт для категорії \"${selectedCategory.displayName}\" поки немає",
						style = MaterialTheme.typography.bodyMedium,
						color = Color.Gray,
						modifier = Modifier.padding(16.dp)
					)
				}
			} else {
				items(
					items = currentWorks,
					key = { work -> work.id }
				) { work ->
					val isAlreadyDone = worksStatusMap[work.id] ?: false

					WorkCard(
						work = work,
						isLeftHanded = userSettings.isLeftHanded,
						enabled = selectedRoom != null && !isAlreadyDone,
						isDone = isAlreadyDone,
						onAddClick = {
							workToProcess = work
							showAddDialog.value = true
						}
					)
				}
			}
		}
	}
}