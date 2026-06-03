package com.ulrezaj.renovum_1.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.repositories.WorkDataRepository
import com.ulrezaj.renovum_1.ui.components.dialogs.DiscountDialog
import com.ulrezaj.renovum_1.ui.components.dialogs.ExportFormatDialog
import com.ulrezaj.renovum_1.ui.components.dialogs.WorkDialog
import com.ulrezaj.renovum_1.ui.components.list_Items.DoneRoomCard
import com.ulrezaj.renovum_1.ui.components.list_Items.DoneWorkCard
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L

@SuppressLint("DefaultLocale")
@Composable
fun DoneScreen(
	roomViewModel: RoomViewModel,
	userSettings: UserSettings
) {
	val groupedWorks by roomViewModel.groupedWorksState.collectAsState()
	val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
	val currentWorkToEdit = roomViewModel.workToEdit

	val showExportDialog = remember { mutableStateOf(false) }
	val showClearDialog = remember { mutableStateOf(false) }

	val context = LocalContext.current

	if (roomViewModel.showDiscountDialog) {
		DiscountDialog(
			initialDiscount = roomViewModel.projectDiscountPercent,
			totalRawSum = roomViewModel.getTotalRawSum(),
			onDismiss = { roomViewModel.showDiscountDialog = false },
			onConfirm = { newDiscount ->
				roomViewModel.updateDiscount(newDiscount)
				roomViewModel.showDiscountDialog = false
			}
		)
	}

	currentWorkToEdit?.let { applied ->
		val service = WorkDataRepository.allWorks.find { it.id == applied.workId }
		val roomOfWork = roomViewModel.rooms.find { it.id == applied.roomId }

		if (service != null && roomOfWork != null) {
			WorkDialog(
				workService = service,
				room = roomOfWork,
				roomViewModel = roomViewModel,
				appliedWork = applied,
				onDismiss = { roomViewModel.workToEdit = null },
				onSave = { newPrice, newQty ->
					roomViewModel.updateAppliedWork(applied, newPrice, newQty)
					roomViewModel.workToEdit = null
				},
				onDelete = {
					roomViewModel.deleteAppliedWork(applied)
					roomViewModel.workToEdit = null
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
								roomViewModel.workToEdit = applied
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

					showExportDialog.value = true
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
						contentDescription = null,
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
				roomViewModel.generateWordReportInBackground(
					context = context,
					isGroupedByRooms = isGroupedByRooms,
					targetAddress = finalAddress,
					customFileName = customFileName,
					userSettings = userSettings
				)
				showClearDialog.value = true
			}
		)
	}

	if (showClearDialog.value) {
		com.ulrezaj.renovum_1.ui.components.dialogs.ClearProjectDialog(
			onDismiss = { showClearDialog.value = false },
			onConfirm = {
				showClearDialog.value = false
				roomViewModel.clearCurrentProject()
				Toast.makeText(context, "Дані об'єкта повністю очищено", Toast.LENGTH_SHORT).show()
			}
		)
	}
}