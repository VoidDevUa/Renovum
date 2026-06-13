package com.void_dev_ua.renovum.ui.screens.calc_screen.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.model.OpeningType
import com.void_dev_ua.renovum.model.RoomEntity

@SuppressLint("DefaultLocale")
@Composable
fun RoomOpeningsPage(currentRoom: RoomEntity) {
	val openings = currentRoom.openings

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
			val expandedStates = remember(currentRoom.id) {
				List(openings.size) { false }.toMutableStateList()
			}

			Column(
				verticalArrangement = Arrangement.spacedBy(12.dp),
				modifier = Modifier.fillMaxWidth()
			) {
				openings.forEachIndexed { index, opening ->
					val w = opening.width.toDouble()
					val h = opening.height.toDouble()
					val area = w * h
					val perimeter = 2 * (w + h)
					val typeName = if (opening.type == OpeningType.WINDOW) "Вікно" else "Двері"
					val isExpanded = expandedStates[index]

					Card(
						modifier = Modifier.fillMaxWidth(),
						colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
						elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
					) {
						Column(modifier = Modifier.fillMaxWidth()) {
							Row(
								modifier = Modifier
									.fillMaxWidth()
									.clickable { expandedStates[index] = !isExpanded }
									.padding(12.dp),
								horizontalArrangement = Arrangement.SpaceBetween,
								verticalAlignment = Alignment.CenterVertically
							) {
								Column {
									Text(
										text = "$typeName №${index + 1}",
										style = MaterialTheme.typography.titleMedium,
										fontWeight = FontWeight.Bold,
										color = MaterialTheme.colorScheme.secondary
									)
									Text(
										text = "Розміри: ${w}м × ${h}м",
										style = MaterialTheme.typography.bodyMedium,
										color = MaterialTheme.colorScheme.onSurfaceVariant
									)
								}
								Icon(
									imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
									contentDescription = if (isExpanded) "Згорнути" else "Розгорнути",
									tint = MaterialTheme.colorScheme.outline
								)
							}

							AnimatedVisibility(visible = isExpanded) {
								Column(
									modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
									verticalArrangement = Arrangement.spacedBy(6.dp)
								) {
									HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

									Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
										Text(text = "Периметр прорізу:", style = MaterialTheme.typography.bodyLarge)
										Text(text = "${String.format("%.2f", perimeter)} м", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
									}
									Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
										Text(text = "Площа прорізу:", style = MaterialTheme.typography.bodyLarge)
										Text(text = "${String.format("%.2f", area)} м²", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
									}
								}
							}
						}
					}
				}
			}
		}
	}
}