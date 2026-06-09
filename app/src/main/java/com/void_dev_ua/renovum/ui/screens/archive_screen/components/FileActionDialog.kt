package com.void_dev_ua.renovum.ui.screens.archive_screen.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.utility.RenovumFileProvider
import java.io.File

@Composable
fun FileActionDialog(
	file: File,
	onDismiss: () -> Unit,
	onDeleteClick: () -> Unit
) {
	val context = LocalContext.current
	val cleanName = file.name
		.replace("Koshtorys_", "")
		.replace(".docx", "")
		.replace("_", " ")

	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(
				text = cleanName,
				style = MaterialTheme.typography.titleMedium
			)
		},
		text = {
			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = "Оберіть дію для цього кошторису:",
					style = MaterialTheme.typography.bodyMedium,
					modifier = Modifier.padding(bottom = 16.dp)
				)

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceEvenly,
					verticalAlignment = Alignment.CenterVertically
				) {
					IconButton(onClick = {
						val isSuccess = RenovumFileProvider.saveToPublicDocuments(context, file)
						if (isSuccess) {
							Toast.makeText(context, "Збережено в /Documents/Renovum", Toast.LENGTH_SHORT).show()
						} else {
							Toast.makeText(context, "Помилка при збереженні файлу", Toast.LENGTH_SHORT).show()
						}
						onDismiss()
					}) {
						Icon(
							imageVector = Icons.Default.Download,
							contentDescription = "Зберегти в Documents",
							tint = MaterialTheme.colorScheme.primary
						)
					}
					IconButton(onClick = {
						RenovumFileProvider.shareFile(context, file)
						onDismiss()
					}) {
						Icon(
							imageVector = Icons.Default.Share,
							contentDescription = "Поділитися файлом",
							tint = MaterialTheme.colorScheme.secondary
						)
					}
					IconButton(onClick = {
						onDismiss()
						onDeleteClick()
					}) {
						Icon(
							imageVector = Icons.Default.Delete,
							contentDescription = "Видалити",
							tint = MaterialTheme.colorScheme.error
						)
					}
				}
			}
		},
		confirmButton = {
			Button(
				onClick = {
					RenovumFileProvider.openFile(context, file)
					onDismiss()
				}
			) {
				Text("Відкрити")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Назад")
			}
		}
	)
}

