package com.ulrezaj.renovum_1.ui.screens.done_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportFormatDialog(
	initialAddress: String,
	initialGroupByRooms: Boolean,
	onDismiss: () -> Unit,
	onConfirm: (Boolean, String, String) -> Unit
){
	val selectedGroupType = remember { mutableStateOf(initialGroupByRooms) }

	val illegalCharacters = remember { listOf('/', '\\', ':', '*', '?', '"', '<', '>', '|') }

	val address = remember { mutableStateOf(initialAddress) }

	val initialSanitizedName = "Koshtorys_${initialAddress.replace(" ", "_")}"
		.filter { it !in illegalCharacters }
	val customFileName = remember { mutableStateOf(initialSanitizedName) }
	val isCustomNameVisible = remember { mutableStateOf(false) }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Експорт даних", style = MaterialTheme.typography.titleLarge) },
		text = {
			Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
				OutlinedTextField(
					value = address.value,
					onValueChange = { newValue ->
						if (newValue.length <= 50) {
							address.value = newValue
							if (!isCustomNameVisible.value) {
								val automaticName = "Koshtorys_${newValue.replace(" ", "_")}"
									.filter { it !in illegalCharacters }
								if (automaticName.length <= 40) {
									customFileName.value = automaticName
								} else {
									customFileName.value = automaticName.take(40)
								}
							}
						}
					},
					label = { Text("Адреса об'єкта в документі") },
					modifier = Modifier.fillMaxWidth(),
					singleLine = true
				)

				if (!isCustomNameVisible.value) {
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.clickable { isCustomNameVisible.value = true }
							.padding(vertical = 4.dp),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.spacedBy(4.dp)
					) {
						Icon(
							imageVector = Icons.Default.Edit,
							contentDescription = "Edit name",
							modifier = Modifier.size(16.dp),
							tint = MaterialTheme.colorScheme.primary
						)
						Text(
							text = "Змінити назву файлу (зараз: ${customFileName.value}.docx)",
							style = MaterialTheme.typography.bodySmall,
							color = MaterialTheme.colorScheme.primary
						)
					}
				}

				AnimatedVisibility(visible = isCustomNameVisible.value) {
					OutlinedTextField(
						value = customFileName.value,
						onValueChange = { newValue ->
							if (newValue.length <= 40 && newValue.none { it in illegalCharacters }) {
								customFileName.value = newValue
							}
						},
						label = { Text("Назва файлу (.docx)") },
						modifier = Modifier.fillMaxWidth(),
						singleLine = true
					)
				}

				HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

				Text("Оберіть тип виводу даних:", style = MaterialTheme.typography.bodyMedium)

				ExportTableTypeSelector(
					isGroupedByRooms = selectedGroupType.value,
					onTypeSelected = { selectedGroupType.value = it }
				)

				Spacer(modifier = Modifier.height(8.dp))
				Text("Оберіть формат файлу:", style = MaterialTheme.typography.bodyMedium)

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(16.dp)
				) {
					OutlinedCard(
						onClick = { /* Поки формат один, вона завжди вибрана за дефолтом */ },
						modifier = Modifier.size(95.dp),
						colors = CardDefaults.outlinedCardColors(
							containerColor = MaterialTheme.colorScheme.tertiaryContainer
						)
					) {
						Column(
							modifier = Modifier.padding(8.dp).fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally,
							verticalArrangement = Arrangement.Center
						) {
							Image(
								painter = painterResource(R.drawable.word_icon),
								contentDescription = "Word Document",
								modifier = Modifier.size(60.dp)
							)
							Spacer(modifier = Modifier.height(4.dp))
							Text(
								text = "Word",
								style = MaterialTheme.typography.labelMedium,
								color = MaterialTheme.colorScheme.onTertiaryContainer
							)
						}
					}
				}
			}
		},
		confirmButton = {
			TextButton(
				onClick = { onConfirm(selectedGroupType.value, address.value, customFileName.value) },
				enabled = address.value.isNotBlank() && customFileName.value.isNotBlank()
			) {
				Text("Згенерувати")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Скасувати")
			}
		}
	)
}