package com.void_dev_ua.renovum.ui.components.dialogs

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.R
import com.void_dev_ua.renovum.model.AppliedWork
import com.void_dev_ua.renovum.model.RoomEntity
import com.void_dev_ua.renovum.model.WorkService
import com.void_dev_ua.renovum.model.OpeningType
import com.void_dev_ua.renovum.model.TargetSurface
import com.void_dev_ua.renovum.viewmodel.RoomViewModel
import com.void_dev_ua.renovum.viewmodel.WorkViewModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun WorkDialog(
	workService: WorkService,
	room: RoomEntity,
	roomViewModel: RoomViewModel,
	workViewModel: WorkViewModel,
	appliedWork: AppliedWork? = null,
	onDismiss: () -> Unit,
	onSave: (price: Double, volume: Double) -> Unit,
	onDelete: (() -> Unit)? = null
) {
	val isEditMode = appliedWork != null

	var priceInput by remember {
		mutableStateOf(appliedWork?.priceAtTime?.toString() ?: "")
	}
	var qtyInput by remember {
		mutableStateOf(appliedWork?.quantity?.toString() ?: "")
	}

	val calcData = roomViewModel.calculateRoomData(room)
	val finalPrice = priceInput.toDoubleOrNull() ?: workService.averagePrice
	val suggestedValue = roomViewModel.getSurfaceValue(workService.targetSurface, calcData)
	val displaySuggestedValue = String.format("%.2f", suggestedValue)
	val finalVolume = qtyInput.toDoubleOrNull() ?: suggestedValue
	val totalSum = finalPrice * finalVolume

	val pagerState = rememberPagerState(initialPage = 1, pageCount = { 2 })
	var expandedSection by remember { mutableIntStateOf(0) }

	val scrollStateInput = rememberScrollState()
	val scrollStateData = rememberScrollState()

	val customTextFieldColors = OutlinedTextFieldDefaults.colors(
		focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
		unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
	)

	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(onClick = { onSave(finalPrice, finalVolume) }) {
				Text(stringResource(R.string.save), color = MaterialTheme.colorScheme.primary)
			}
		},
		dismissButton = {
			Row(verticalAlignment = Alignment.CenterVertically) {
				if (isEditMode && onDelete != null) {
					IconButton(onClick = onDelete) {
						Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete), tint = MaterialTheme.colorScheme.error)
					}
				}
				TextButton(onClick = onDismiss) {
					Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
				}
			}
		},
		title = {
			Column {
				Text(
					text = if (isEditMode) stringResource(R.string.edit_work) else stringResource(R.string.add_work),
					style = MaterialTheme.typography.labelMedium,
					color = if (isEditMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
				)
				Text(
					workService.name,
					style = MaterialTheme.typography.titleLarge,
					fontWeight = FontWeight.Bold
				)
			}
		},
		text = {
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.background(
							color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
							shape = MaterialTheme.shapes.small
						)
						.padding(vertical = 6.dp),
					contentAlignment = Alignment.Center
				) {
					val labelText = if (pagerState.currentPage == 1) {
						stringResource(R.string.back_to_room_data)
					} else if (isEditMode) {
						stringResource(R.string.edit_work) + " →"
					} else {
						stringResource(R.string.add_work) + " →"
					}
					Text(
						text = labelText,
						style = MaterialTheme.typography.labelMedium,
						fontWeight = FontWeight.Bold,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}

				HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

				HorizontalPager(
					state = pagerState,
					modifier = Modifier
						.fillMaxWidth()
						.height(340.dp)
				) { page ->
					when (page) {
						0 -> {
							Column(
								modifier = Modifier
									.fillMaxSize()
									.verticalScroll(scrollStateData),
								verticalArrangement = Arrangement.spacedBy(8.dp)
							) {
								Text(text = stringResource(R.string.room_name_label, room.name), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 4.dp))

								AccordionHeader(
									title = stringResource(R.string.room_data),
									isExpanded = expandedSection == 0,
									onClick = { expandedSection = if (expandedSection == 0) -1 else 0 }
								)
								if (expandedSection == 0) {
									Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
										val rows = listOf(
											stringResource(R.string.floor_area_label) to "${String.format("%.2f", calcData.floorArea)} ${stringResource(R.string.unit_m2)}",
											stringResource(R.string.perimeter_label) to "${String.format("%.2f", calcData.perimeter)} ${stringResource(R.string.unit_m)}",
											stringResource(R.string.all_openings_area_label) to "${String.format("%.2f", calcData.allOpeningsArea)} ${stringResource(R.string.unit_m2)}",
											stringResource(R.string.wall_area_label) to "${String.format("%.2f", calcData.wallArea)} ${stringResource(R.string.unit_m2)}",
											stringResource(R.string.clean_wall_area_label) to "${String.format("%.2f", calcData.cleanWallArea)} ${stringResource(R.string.unit_m2)}"
										)
										rows.forEach { (label, value) ->
											Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
												Text(label, style = MaterialTheme.typography.bodySmall)
												Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
											}
										}
										calcData.extra.forEach { (name, value) ->
											Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
												Text("$name:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
												Text("${String.format("%.2f", value)} м²", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
											}
										}
									}
								}

								AccordionHeader(
									title = stringResource(R.string.room_openings),
									isExpanded = expandedSection == 1,
									onClick = { expandedSection = if (expandedSection == 1) -1 else 1 }
								)
								if (expandedSection == 1) {
									Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
										if (room.openings.isNotEmpty()) {
											HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))

											val windows = room.openings.filter { it.type == OpeningType.WINDOW }
											val doors = room.openings.filter { it.type == OpeningType.DOOR }

											windows.forEachIndexed { idx, op ->
												val area = (op.width.toDoubleOrNull() ?: 0.0) * (op.height.toDoubleOrNull() ?: 0.0)
												Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
													Text(stringResource(R.string.window_label, idx + 1, op.width, op.height), style = MaterialTheme.typography.bodySmall)
													Text("${String.format("%.2f", area)} ${stringResource(R.string.unit_m2)}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
												}
											}

											doors.forEachIndexed { idx, op ->
												val area = (op.width.toDoubleOrNull() ?: 0.0) * (op.height.toDoubleOrNull() ?: 0.0)
												Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
													Text(stringResource(R.string.door_label, idx + 1, op.width, op.height), style = MaterialTheme.typography.bodySmall)
													Text("${String.format("%.2f", area)} ${stringResource(R.string.unit_m2)}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
												}
											}
										} else {
											Text(stringResource(R.string.no_openings_in_room), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
										}
									}
								}
							}
						}
						1 -> {
							Column(
								modifier = Modifier
									.fillMaxSize()
									.verticalScroll(scrollStateInput),
								verticalArrangement = Arrangement.spacedBy(12.dp)
							) {
								if (workService.minPrice > 0.0 || workService.maxPrice > 0.0) {
									Text(
										text = stringResource(R.string.price_range, workService.minPrice.toInt(), workService.maxPrice.toInt(), workService.unit.displayName),
										style = MaterialTheme.typography.bodySmall,
										color = MaterialTheme.colorScheme.onSurface
									)
								}

								Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
									Text(
										text = stringResource(R.string.price_per_unit),
										style = MaterialTheme.typography.labelMedium,
										color = MaterialTheme.colorScheme.onSurface,
										fontWeight = FontWeight.Medium
									)
									OutlinedTextField(
										value = priceInput,
										onValueChange = { newValue ->
											if (newValue.length <= 7 && newValue.all { char -> char.isDigit() || char == '.' }) {
												priceInput = newValue
											}
										},
										placeholder = {
											Text(
												text = if (workService.averagePrice > 0.0) "${workService.averagePrice.toInt()}" else stringResource(R.string.enter_price)
											)
										},
										suffix = { Text(stringResource(R.string.currency_per_unit, workService.unit.displayName)) },
										singleLine = true,
										keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
										modifier = Modifier.fillMaxWidth(),
										colors = customTextFieldColors
									)
								}

								val options = roomViewModel.getAvailableOptions(workService.targetSurface, calcData)

								Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
									Text(
										text = stringResource(R.string.work_volume),
										style = MaterialTheme.typography.labelMedium,
										color = MaterialTheme.colorScheme.onSurface,
										fontWeight = FontWeight.Medium
									)
									OutlinedTextField(
										value = qtyInput,
										onValueChange = { newValue ->
											if (newValue.length <= 6 && newValue.all { char -> char.isDigit() || char == '.' }) {
												qtyInput = newValue
											}
										},
										placeholder = {
											Text(text = if (workService.targetSurface != TargetSurface.NONE) displaySuggestedValue else "1.0")
										},
										suffix = { Text(workService.unit.displayName) },
										singleLine = true,
										keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
										modifier = Modifier.fillMaxWidth(),
										colors = customTextFieldColors
									)
									if (options.isNotEmpty()) {
										Column(
											modifier = Modifier
												.fillMaxWidth()
												.padding(top = 8.dp),
											verticalArrangement = Arrangement.spacedBy(4.dp)
										) {
											options.forEach { (label, value) ->
												Card(
													onClick = {
														val formatted = String.format("%.2f", value).replace(",", ".")
														qtyInput = if (formatted.length <= 6) formatted else formatted.take(6)
													},
													modifier = Modifier
														.fillMaxWidth()
														.height(36.dp),
													colors = CardDefaults.cardColors(
														containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
													),
													shape = MaterialTheme.shapes.medium
												) {
													Box(
														modifier = Modifier.fillMaxSize(),
														contentAlignment = Alignment.Center
													) {
														Text(
															text = label,
															style = MaterialTheme.typography.titleMedium,
															fontWeight = FontWeight.Medium,
															color = MaterialTheme.colorScheme.onSurfaceVariant
														)
													}
												}
											}
										}
									}
								}

								Card(
									modifier = Modifier.fillMaxWidth(),
									colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
								) {
									Row(
										modifier = Modifier.fillMaxWidth().padding(8.dp),
										horizontalArrangement = Arrangement.SpaceBetween,
										verticalAlignment = Alignment.CenterVertically
									) {
										Text(stringResource(R.string.total_label), style = MaterialTheme.typography.bodyMedium)
										Text(
											text = "${String.format("%.2f", totalSum)} ₴",
											style = MaterialTheme.typography.titleMedium,
											color = MaterialTheme.colorScheme.primary,
											fontWeight = FontWeight.Bold
										)
									}
								}
							}
						}
					}
				}
			}
		}
	)
}

/**
 * Кнопка-шапка для списків, що розгортаються (Accordion)
 */
@Composable
private fun AccordionHeader(
	title: String,
	isExpanded: Boolean,
	onClick: () -> Unit
) {
	val rotationState by animateFloatAsState(
		targetValue = if (isExpanded) 180f else 0f,
		label = "RotationAnimation"
	)

	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() },
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 12.dp, vertical = 8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.bodyMedium,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.onSurface
			)
			Icon(
				imageVector = Icons.Default.ArrowDropDown,
				contentDescription = "Розгорнути/Згорнути",
				modifier = Modifier.rotate(rotationState),
				tint = MaterialTheme.colorScheme.primary
			)
		}
	}
}