package com.void_dev_ua.renovum.presentation.ui.screens.done_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.data.UserSettingsManager
import com.void_dev_ua.renovum.presentation.ui.screens.done_screen.components.ClearProjectDialog
import com.void_dev_ua.renovum.presentation.ui.screens.done_screen.components.DiscountDialog
import com.void_dev_ua.renovum.presentation.ui.screens.done_screen.components.ExportFormatDialog
import com.void_dev_ua.renovum.presentation.ui.components.dialogs.WorkDialog
import com.void_dev_ua.renovum.presentation.ui.screens.done_screen.components.DoneRoomCard
import com.void_dev_ua.renovum.presentation.ui.screens.done_screen.components.DoneWorkCard
import com.void_dev_ua.renovum.presentation.viewmodel.DoneScreenViewModel
import com.void_dev_ua.renovum.presentation.viewmodel.ReportViewModel
import com.void_dev_ua.renovum.core.util.L
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.launch

@SuppressLint("DefaultLocale")
@Composable
fun DoneScreen(
	userSettings: UserSettings,
	viewModel: DoneScreenViewModel = hiltViewModel(),
	reportViewModel: ReportViewModel = hiltViewModel()
) {
	val rooms by viewModel.rooms.collectAsState()
	val groupedWorks by viewModel.groupedWorksState.collectAsState()
	val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
	val currentWorkToEdit = viewModel.workToEdit
	val scope = rememberCoroutineScope()

	val showExportDialog = remember { mutableStateOf(false) }
	val showClearDialog = remember { mutableStateOf(false) }

	val context = LocalContext.current

	if (viewModel.showDiscountDialog) {
		DiscountDialog(
			initialDiscount = viewModel.projectDiscountPercent.collectAsState().value,
			totalRawSum = viewModel.totalRawSumState.collectAsState().value,
			onDismiss = { viewModel.showDiscountDialog = false },
			onConfirm = { newDiscount ->
				viewModel.updateDiscount(newDiscount)
				viewModel.showDiscountDialog = false
			}
		)
	}

	currentWorkToEdit?.let { applied ->
		val service = viewModel.getWorkServiceById(applied.workId)
		val roomOfWork = rooms.find { it.id == applied.roomId }

		if (service != null && roomOfWork != null) {
			WorkDialog(
				workService = service,
				room = roomOfWork,
				appliedWork = applied,
				onDismiss = { viewModel.workToEdit = null },
				onSave = { newPrice, newQty ->
					viewModel.updateAppliedWork(applied, newPrice, newQty)
					viewModel.workToEdit = null
				},
				onDelete = {
					viewModel.deleteAppliedWork(applied)
					viewModel.workToEdit = null
				}
			)
		}
	}

	Column(modifier = Modifier.fillMaxSize()) {
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f),
			contentPadding = PaddingValues(12.dp),
			verticalArrangement = Arrangement.spacedBy(4.dp)
		) {
			groupedWorks.forEach { (room, works) ->
				val isExpanded = expandedStates[room.id] ?: true
				val roomTotal = works.sumOf { it.first.priceAtTime * it.first.quantity }

				item(key = room.id) {
					DoneRoomCard(
						roomName = room.name,
						roomTotal = roomTotal,
						isExpanded = isExpanded,
						onExpandClick = {
							expandedStates[room.id] = !isExpanded
						}
					)
				}

				if (isExpanded) {
					items(works) { (applied, service) ->
						DoneWorkCard(
							service = service,
							applied = applied,
							onClick = {
								viewModel.workToEdit = applied
								L.d("Click to edit: ${service.name}")
							}
						)
					}
				}
			}
		}

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp, vertical = 12.dp)
		) {
			Button(
				onClick = {
					L.click("DoneScreen: Натиснуто кнопку експорту")

					if (groupedWorks.isEmpty()) {
						Toast.makeText(context, "Неможливо створити звіт: додайте кімнати та роботи", Toast.LENGTH_LONG).show()
					} else {
						showExportDialog.value = true
					}
				},
				modifier = Modifier
					.fillMaxWidth()
					.height(52.dp),
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.tertiary,
					contentColor = MaterialTheme.colorScheme.onTertiary
				),
				shape = MaterialTheme.shapes.medium
			) {
				Row(
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Default.Description,
						contentDescription = "Іконка звіту",
						modifier = Modifier.size(24.dp)
					)
					Spacer(modifier = Modifier.width(8.dp))
					Text(
						text = "Сформувати звіт Word",
						style = MaterialTheme.typography.titleMedium
					)
				}
			}
		}
	}

	if (showExportDialog.value) {
		ExportFormatDialog(
			initialAddress = userSettings.currentObjectAddress,
			initialGroupByRooms = userSettings.groupWordByRooms,
			onDismiss = { showExportDialog.value = false },
			onConfirm = { isGroupedByRooms, finalAddress, customFileName ->
				showExportDialog.value = false
				reportViewModel.generateWordReportInBackground(
					context = context,
					isGroupedByRooms = isGroupedByRooms,
					targetAddress = finalAddress,
					customFileName = customFileName,
					userSettings = userSettings,
					groupedWorks = groupedWorks,
					totalRawSum = viewModel.totalRawSumState.value,
					projectDiscountPercent = viewModel.projectDiscountPercent.value,
					totalDiscountedSum = viewModel.totalDiscountedSumState.value
				)
				showClearDialog.value = true
			}
		)
	}

	if (showClearDialog.value) {
		ClearProjectDialog(
			onDismiss = { showClearDialog.value = false },
			onConfirm = {
				showClearDialog.value = false
				viewModel.clearCurrentProject(
					onFinished = {
						val manager = UserSettingsManager(context)
						// Correct way to clear address in settings
						scope.launch {
							manager.saveSettings(userSettings.copy(currentObjectAddress = ""))
						}
						Toast.makeText(context, "Дані об'єкта повністю очищено", Toast.LENGTH_SHORT).show()
					}
				)
			}
		)
	}
}
