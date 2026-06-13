package com.void_dev_ua.renovum.ui.screens.archive_screen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.R
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.viewmodel.RoomViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("LocalContextConfigurationRead")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FileCard(
	file: File,
	isSelected: Boolean,
	isSelectMode: Boolean,
	userSettings: UserSettings,
	roomViewModel: RoomViewModel,
	onFileClick: (File) -> Unit
) {
	val context = LocalContext.current
	val platformLocale = context.resources.configuration.locales[0]

	val cleanName = remember(file.name) {
		file.name
			.replace("Koshtorys_", "")
			.replace(".docx", "")
			.replace("_", " ")
	}

	val fileDate = remember(file.lastModified(), platformLocale) {
		val sdf = SimpleDateFormat("dd.MM.yyyy", platformLocale)
		sdf.format(Date(file.lastModified()))
	}

	Card(
		modifier = Modifier
			.fillMaxWidth()
			.combinedClickable(
				onClick = {
					if (isSelectMode) {
						roomViewModel.toggleArchiveFileSelection(file)
					} else {
						onFileClick(file)
					}
				},
				onLongClick = {
					roomViewModel.toggleArchiveFileSelection(file)
				}
			),
		colors = CardDefaults.cardColors(
			containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
			else MaterialTheme.colorScheme.surfaceVariant
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(12.dp),
			verticalAlignment = Alignment.CenterVertically
		) {

			if (userSettings.isLeftHanded) {
				AnimatedCheckbox(visible = isSelectMode, checked = isSelected) {
					roomViewModel.toggleArchiveFileSelection(file)
				}
			}

			Image(
				painter = painterResource(R.drawable.word_icon),
				contentDescription = "Word File",
				modifier = Modifier.size(40.dp)
			)

			Spacer(modifier = Modifier.width(12.dp))

			Column(modifier = Modifier.weight(1f)) {
				Text(
					text = cleanName,
					style = MaterialTheme.typography.titleMedium,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Spacer(modifier = Modifier.height(4.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Text(
						text = "Адреса: $cleanName",
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						maxLines = 1,
						modifier = Modifier.weight(1f)
					)
					Text(
						text = fileDate,
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}

			if (!userSettings.isLeftHanded) {
				Spacer(modifier = Modifier.width(12.dp))
				AnimatedCheckbox(visible = isSelectMode, checked = isSelected) {
					roomViewModel.toggleArchiveFileSelection(file)
				}
			}
		}
	}
}