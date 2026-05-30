package com.ulrezaj.renovum_1.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.ReportData
import com.ulrezaj.renovum_1.data.repositories.WordExportManager
import com.ulrezaj.renovum_1.data.repositories.WorkDataRepository
import com.ulrezaj.renovum_1.ui.components.dialogs.DiscountDialog
import com.ulrezaj.renovum_1.ui.components.dialogs.WorkDialog
import com.ulrezaj.renovum_1.ui.components.list_Items.DoneRoomCard
import com.ulrezaj.renovum_1.ui.components.list_Items.DoneWorkCard
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("DefaultLocale")
@Composable
fun DoneScreen(roomViewModel: RoomViewModel) {
	val groupedWorks by roomViewModel.groupedWorksState.collectAsState()

	val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

	val currentWorkToEdit = roomViewModel.workToEdit

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
			val context = LocalContext.current

			Button(
				onClick = {
					L.click("DoneScreen: Сформувати звіт Word")

					val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
					val currentDateString = dateFormat.format(java.util.Date())

					val reportData = ReportData(
						projectName = "Об'єкт №${roomViewModel.rooms.size}",
						dateString = currentDateString,
						roomsWithWorks = groupedWorks,
						totalRawSum = roomViewModel.getTotalRawSum(),
						discountPercent = roomViewModel.projectDiscountPercent,
						totalDiscountedSum = roomViewModel.getTotalDiscountedSum()
					)

					val temporarySettings = UserSettings(
						groupWordByRooms = true,
						showDiscountInWord = true
					)

					val wordFile = WordExportManager.createWordDocument(
						context = context,
						data = reportData,
						settings = temporarySettings
					)

					if (wordFile != null && wordFile.exists()) {
						L.d("DoneScreen: УСПІХ! Файл готовий. Надсилаємо...")

						try {
							val fileUri: Uri = FileProvider.getUriForFile(
								context,
								"${context.packageName}.fileprovider",
								wordFile
							)

							val shareIntent = Intent(Intent.ACTION_SEND).apply {
								type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
								putExtra(Intent.EXTRA_STREAM, fileUri)
								addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
							}

							context.startActivity(Intent.createChooser(shareIntent, "Надіслати кошторис..."))
						} catch (e: Exception) {
							L.e("DoneScreen: Помилка запуску ShareIntent", e)
						}
					} else {
						L.e("DoneScreen: Не вдалося згенерувати або знайти файл")
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
}