package com.ulrezaj.renovum_1.ui.screens.calc_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.ui.screens.calc_screen.utils.CalcResultLine
import com.ulrezaj.renovum_1.ui.viewmodels.CalculatedData

@SuppressLint("DefaultLocale")
@Composable
fun RoomResultsPage(currentRoom: RoomEntity, data: CalculatedData) {
	Column(
		verticalArrangement = Arrangement.spacedBy(12.dp),
		modifier = Modifier.fillMaxWidth()
	) {
		val totalOpeningsArea = currentRoom.openings.sumOf { it.width.toDouble() * it.height.toDouble() }
		var isInputsExpanded by remember { mutableStateOf(false) }

		Card(
			modifier = Modifier.fillMaxWidth(),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
			elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
		) {
			Column(
				modifier = Modifier.padding(16.dp),
				verticalArrangement = Arrangement.spacedBy(10.dp)
			) {
				CalcResultLine(
					label = "Периметр підлоги:",
					value = "${String.format("%.2f", data.perimeter)} м"
				)
				CalcResultLine(
					label = "Площа підлоги:",
					value = "${String.format("%.2f", data.floorArea)} м²"
				)
				data.extra.forEach { (name, value) ->
					CalcResultLine(label = "$name:", value = "${String.format("%.2f", value)} м²")
				}
				CalcResultLine(
					label = "Загальна площа прорізів:",
					value = "${String.format("%.2f", totalOpeningsArea)} м²"
				)
				CalcResultLine(
					label = "Загальна площа стін:",
					value = "${String.format("%.2f", data.wallArea)} м²"
				)

				HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

				CalcResultLine(
					label = "Чиста площа стін:",
					value = "${String.format("%.2f", data.cleanWallArea)} м²",
					isMain = true
				)
			}
		}

		Card(
			modifier = Modifier.fillMaxWidth(),
			elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
		) {
			Column(modifier = Modifier.fillMaxWidth()) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.clickable { isInputsExpanded = !isInputsExpanded }
						.padding(16.dp),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "Вхідні розміри",
						style = MaterialTheme.typography.titleSmall,
						fontWeight = FontWeight.Bold,
						color = MaterialTheme.colorScheme.secondary
					)
					Icon(
						imageVector = if (isInputsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
						contentDescription = if (isInputsExpanded) "Згорнути" else "Розгорнути",
						tint = MaterialTheme.colorScheme.secondary
					)
				}

				AnimatedVisibility(visible = isInputsExpanded) {
					Column(
						modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
						verticalArrangement = Arrangement.spacedBy(10.dp)
					) {
						HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))

						val allInputs = currentRoom.params.toMap()
						allInputs.forEach { (label, value) ->
							CalcResultLine(label = "$label:", value = "$value м")
						}

						HorizontalDivider(
							color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
							thickness = 0.5.dp
						)

						CalcResultLine(
							label = "Висота приміщення:",
							value = "${currentRoom.params.roomHeight} м",
							isMain = true
						)
					}
				}
			}
		}
	}
}