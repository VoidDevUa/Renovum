package com.ulrezaj.renovum_1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.OpeningType
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.ui.components.RoomSchemaPainter
import com.ulrezaj.renovum_1.ui.components.topAppBar.elements.RoomSelectorDropdown
import com.ulrezaj.renovum_1.ui.viewmodels.CalculatedData
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L
import kotlinx.coroutines.launch

@Composable
fun CalcScreen(
	currentRoom: RoomEntity,
	roomViewModel: RoomViewModel,
	allRooms: List<RoomEntity>,
	userSettings: UserSettings,
	onRoomSelected: (RoomEntity) -> Unit
) {
	LaunchedEffect(currentRoom.id) {
		L.d("CalcScreen: Active room is ${currentRoom.name} (ID: ${currentRoom.id})")
	}

	val data = roomViewModel.calculateRoomData(currentRoom)

	val pagerState = rememberPagerState(pageCount = { 3 })
	val scope = rememberCoroutineScope()

	Column(modifier = Modifier
		.fillMaxSize()
		.padding(horizontal = 16.dp, vertical = 8.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			val selector = @Composable {
				RoomSelectorDropdown(
					selectedRoom = currentRoom,
					rooms = allRooms,
					onRoomSelected = onRoomSelected,
					isOutlined = true
				)
			}
			val title = @Composable {
				Text(text = "Форма: ${currentRoom.shapeType.getDisplayName()}", style = MaterialTheme.typography.bodyMedium)
			}

			if (userSettings.isLeftHanded) {
				selector()
				title()
			} else {
				title()
				selector()
			}
		}

		RoomSchemaPainter(
			shapeType = currentRoom.shapeType,
			focusedField = null,
			paramValues = currentRoom.params.toMap(),
			modifier = Modifier.height(220.dp).fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(8.dp))

		Card(
			modifier = Modifier.fillMaxWidth().height(48.dp),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
		) {
			Row(
				modifier = Modifier.fillMaxSize(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				IconButton(
					onClick = {
						if (pagerState.currentPage > 0) {
							scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
						}
					},
					enabled = pagerState.currentPage > 0
				) {
					Icon(
						imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
						contentDescription = "Назад",
						tint = if (pagerState.currentPage > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
					)
				}

				val headerText = when (pagerState.currentPage) {
					0 -> "Вхідні параметри"
					1 -> "Параметри прорізів"
					else -> "Результати обчислень"
				}

				Text(
					text = headerText,
					style = MaterialTheme.typography.titleSmall,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.primary,
					modifier = Modifier.weight(1f),
					textAlign = TextAlign.Center
				)

				IconButton(
					onClick = {
						if (pagerState.currentPage < 2) {
							scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
						}
					},
					enabled = pagerState.currentPage < 2
				) {
					Icon(
						imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
						contentDescription = "Вперед",
						tint = if (pagerState.currentPage < 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
					)
				}
			}
		}

		Spacer(modifier = Modifier.height(12.dp))

		HorizontalPager(
			state = pagerState,
			modifier = Modifier.weight(1f)
		) { page ->
			Column(
				modifier = Modifier
					.fillMaxSize()
					.verticalScroll(rememberScrollState()),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				when (page) {
					0 -> InputParamsPage(currentRoom = currentRoom)
					1 -> OpeningsPage(currentRoom = currentRoom)
					2 -> ResultsPage(data = data)
				}
			}
		}
	}
}

@Composable
fun InputParamsPage(currentRoom: RoomEntity) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
	) {
		Column(
			modifier = Modifier.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp)
		) {
			val allInputs = currentRoom.params.toMap()

			allInputs.forEach { (label, value) ->
				ResultLine(label = "$label:", value = "$value м")
			}

			HorizontalDivider(
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
				thickness = 0.5.dp
			)

			ResultLine(
				label = "Висота приміщення:",
				value = "${currentRoom.params.roomHeight} м",
				isMain = true
			)
		}
	}
}

@SuppressLint("DefaultLocale")
@Composable
fun OpeningsPage(currentRoom: RoomEntity) {
	val openings = currentRoom.openings
	val totalOpeningsArea = openings.sumOf { it.width.toDouble() * it.height.toDouble() }

	Column(
		verticalArrangement = Arrangement.spacedBy(12.dp),
		modifier = Modifier.fillMaxWidth()
	) {
		if (openings.isEmpty()) {
			Card(
				modifier = Modifier.fillMaxWidth(),
				colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
			) {
				Text(
					text = "У цій кімнаті немає вікон чи дверей",
					modifier = Modifier.padding(16.dp),
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.outline,
					textAlign = TextAlign.Center
				)
			}
		} else {
			openings.forEachIndexed { index, opening ->
				val w = opening.width.toDouble()
				val h = opening.height.toDouble()
				val area = w * h
				val perimeter = 2 * (w + h)
				val typeName = if (opening.type == OpeningType.WINDOW) "Вікно" else "Двері"

				Card(
					modifier = Modifier.fillMaxWidth(),
					colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
				) {
					Column(
						modifier = Modifier.padding(12.dp),
						verticalArrangement = Arrangement.spacedBy(6.dp)
					) {
						Text(
							text = "$typeName №${index + 1}",
							style = MaterialTheme.typography.titleMedium,
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.secondary
						)
						HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

						ResultLine(label = "Розміри:", value = "${w}м × ${h}м")
						ResultLine(label = "Периметр прорізу:", value = "${String.format("%.2f", perimeter)} м")
						ResultLine(label = "Площа прорізу:", value = "${String.format("%.2f", area)} м²")
					}
				}
			}

			Card(
				modifier = Modifier.fillMaxWidth(),
				colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
			) {
				Column(modifier = Modifier.padding(16.dp)) {
					ResultLine(
						label = "Загальна площа прорізів (S):",
						value = "${String.format("%.2f", totalOpeningsArea)} м²",
						isMain = true
					)
				}
			}
		}
	}
}

@SuppressLint("DefaultLocale")
@Composable
fun ResultsPage(data: CalculatedData) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
	) {
		Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
			ResultLine(label = "Периметр підлоги:", value = "${String.format("%.2f", data.perimeter)} м")
			ResultLine(label = "Площа підлоги:", value = "${String.format("%.2f", data.floorArea)} м²")

			data.extra.forEach { (name, value) ->
				ResultLine(label = "$name:", value = "${String.format("%.2f", value)} м²")
			}

			HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

			ResultLine(
				label = "Площа стін без прорізів:",
				value = "${String.format("%.2f", data.wallArea)} м²",
				isMain = true
			)
		}
	}
}

@Composable
fun ResultLine(label: String, value: String, isMain: Boolean = false) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
		Text(
			text = label,
			style = if (isMain) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
			fontWeight = if (isMain) FontWeight.ExtraBold else FontWeight.Normal
		)
		Text(
			text = value,
			style = if (isMain) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyLarge,
			fontWeight = FontWeight.Bold,
			color = if (isMain) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
		)
	}
}