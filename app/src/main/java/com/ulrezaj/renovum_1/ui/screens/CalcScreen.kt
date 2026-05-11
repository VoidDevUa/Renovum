package com.ulrezaj.renovum_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.OpeningType
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.ui.components.RoomSchemaPainter
import com.ulrezaj.renovum_1.ui.components.topAppBar.elements.RoomSelectorDropdown
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L

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

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(rememberScrollState())
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
				Text(text = "Форма: ${currentRoom.shapeType.getDisplayName()}")
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
			modifier = Modifier.height(250.dp)
		)

		Text(
			"Вхідні параметри",
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.primary
		)
		Card(modifier = Modifier.fillMaxWidth()) {
			Column(
				modifier = Modifier.padding(12.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				val allInputs = currentRoom.params.toMap()

				allInputs.entries.chunked(3).forEach { rowItems ->
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
					) {
						rowItems.forEach { entry ->
							ParamBox(label = entry.key, value = "${entry.value} м")
						}
					}
				}

				HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 0.5.dp)
				ParamBox(label = "Висота приміщення", value = "${currentRoom.params.roomHeight} м")
			}
		}

		val openingsArea = currentRoom.openings.sumOf { it.width.toDouble() * it.height.toDouble() }
		val winCount = currentRoom.openings.count { it.type == OpeningType.WINDOW }
		val doorCount = currentRoom.openings.count { it.type == OpeningType.DOOR }

		Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
			Row(
				modifier = Modifier.padding(12.dp).fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceAround
			) {
				ParamBox(label = "Вікна", value = "$winCount шт")
				ParamBox(label = "Двері", value = "$doorCount шт")
				ParamBox(label = "S прорізів", value = "${"%.2f".format(openingsArea)} м²")
			}
		}

		Text("Результати обчислень", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
		Card(
			modifier = Modifier.fillMaxWidth(),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
			elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
		) {
			Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
				ResultLine(label = "Периметр:", value = "${"%.2f".format(data.perimeter)} м")
				ResultLine(label = "Площа підлоги:", value = "${"%.2f".format(data.floorArea)} м²")

				data.extra.forEach { (name, value) ->
					ResultLine(label = "$name:", value = "${"%.2f".format(value)} м²")
				}

				HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

				ResultLine(
					label = "Площа стін без прорізів:",
					value = "${"%.2f".format(data.wallArea)} м²",
					isMain = true
				)
			}
		}
	}
}

@Composable
fun ParamBox(label: String, value: String) {
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
		Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
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