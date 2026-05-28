package com.ulrezaj.renovum_1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.repositories.WorkDataRepository
import com.ulrezaj.renovum_1.ui.components.dialogs.DiscountDialog
import com.ulrezaj.renovum_1.ui.components.dialogs.WorkDialog
import com.ulrezaj.renovum_1.ui.components.list_Items.DoneRoomCard
import com.ulrezaj.renovum_1.ui.components.list_Items.DoneWorkCard
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L

@SuppressLint("DefaultLocale")
@Composable
fun DoneScreen(roomViewModel: RoomViewModel) {
	val groupedWorks = roomViewModel.getGroupedWorks()

	val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

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

	roomViewModel.workToEdit?.let { applied ->
		val service = WorkDataRepository.allWorks.find { it.id == applied.workId }
		val roomOfWork = roomViewModel.rooms.find { it.id == applied.roomId }

		if (service != null && roomOfWork != null) {
			WorkDialog(
				workService = service,
				room = roomOfWork,
				roomViewModel = roomViewModel,
				appliedWork = applied, // Передаємо об'єкт -> режим РЕДАГУВАННЯ
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
			modifier = Modifier.fillMaxSize(),
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
	}
}