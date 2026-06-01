package com.ulrezaj.renovum_1.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.ui.components.ExportTableTypeSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportFormatDialog(
	initialGroupByRooms: Boolean,
	onDismiss: () -> Unit,
	onConfirm: (Boolean) -> Unit
){
	val selectedGroupType = remember { mutableStateOf(initialGroupByRooms) }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Експорт даних", style = MaterialTheme.typography.titleLarge) },
		text = {
			Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
						// Підсвічуємо її фірмовим третинним кольором додатка
						colors = CardDefaults.outlinedCardColors(
							containerColor = MaterialTheme.colorScheme.tertiaryContainer
						)
					) {
						Column(
							modifier = Modifier.padding(8.dp).fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally,
							verticalArrangement = Arrangement.Center
						) {
							Icon(
								imageVector = Icons.Default.Description,
								contentDescription = "Word Document",
								modifier = Modifier.size(36.dp),
								tint = MaterialTheme.colorScheme.onTertiaryContainer
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
			TextButton(onClick = { onConfirm(selectedGroupType.value) }) {
				Text("Зберегти")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Скасувати")
			}
		}
	)
}