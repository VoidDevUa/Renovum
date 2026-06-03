package com.ulrezaj.renovum_1.ui.screens.archive_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.ui.screens.archive_screen.components.DeleteFilesDialog
import com.ulrezaj.renovum_1.ui.screens.archive_screen.components.FileActionDialog
import com.ulrezaj.renovum_1.ui.screens.archive_screen.components.FileCard
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArchiveScreen(
	roomViewModel: RoomViewModel,
	userSettings: UserSettings
) {
	val context = LocalContext.current
	val files = roomViewModel.archiveFiles
	val selectedFiles = roomViewModel.selectedArchiveFiles
	val isSelectMode = roomViewModel.isArchiveSelectMode

	val showDeleteDialog = remember { mutableStateOf(false) }
	val clickedFileForActions = remember { mutableStateOf<File?>(null) }
	val singleFileToDelete = remember { mutableStateOf<File?>(null) }

	LaunchedEffect(Unit) {
		roomViewModel.loadArchiveFiles(context)
	}

	DisposableEffect(Unit) {
		onDispose {
			roomViewModel.clearArchiveSelection()
		}
	}

	Box(modifier = Modifier.fillMaxSize()) {
		if (files.isEmpty()) {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				Text(
					text = "Архів порожній",
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		} else {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 88.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				items(files, key = { it.absolutePath }) { file ->
					FileCard(
						file = file,
						isSelected = selectedFiles.contains(file),
						isSelectMode = isSelectMode,
						userSettings = userSettings,
						roomViewModel = roomViewModel,
						onFileClick = { clickedFileForActions.value = it }
					)
				}
			}
		}

		val fabAlignment = if (!userSettings.isLeftHanded) Alignment.BottomEnd else Alignment.BottomStart

		AnimatedVisibility(
			visible = isSelectMode && selectedFiles.isNotEmpty(),
			enter = slideInHorizontally(animationSpec = tween(300)) { if (userSettings.isLeftHanded) -it else it },
			exit = slideOutHorizontally(animationSpec = tween(300)) { if (userSettings.isLeftHanded) -it else it },
			modifier = Modifier
				.align(fabAlignment)
				.padding(24.dp)
		) {
			FloatingActionButton(
				onClick = {
					singleFileToDelete.value = null
					showDeleteDialog.value = true
						  },
				containerColor = MaterialTheme.colorScheme.errorContainer,
				contentColor = MaterialTheme.colorScheme.onErrorContainer
			) {
				Icon(Icons.Default.Delete, contentDescription = "Видалити вибрані")
			}
		}
	}

	clickedFileForActions.value?.let { file ->
		FileActionDialog(
			file = file,
			onDismiss = { clickedFileForActions.value = null },
			onDeleteClick = {
				singleFileToDelete.value = file
				showDeleteDialog.value = true
			}
		)
	}

	if (showDeleteDialog.value) {
		val count = if (singleFileToDelete.value != null) 1 else selectedFiles.size

		DeleteFilesDialog(
			selectedCount = count,
			onConfirm = {
				if (singleFileToDelete.value != null) {
					if (singleFileToDelete.value!!.exists()) singleFileToDelete.value!!.delete()
					roomViewModel.loadArchiveFiles(context)
				} else {
					roomViewModel.deleteSelectedArchiveFiles(context)
				}
				showDeleteDialog.value = false
				singleFileToDelete.value = null
			},
			onDismiss = {
				showDeleteDialog.value = false
				singleFileToDelete.value = null
			}
		)
	}
}