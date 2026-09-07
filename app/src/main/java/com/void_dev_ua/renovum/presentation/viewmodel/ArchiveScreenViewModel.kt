package com.void_dev_ua.renovum.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ArchiveScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    val archiveFiles = mutableStateListOf<File>()
    val selectedArchiveFiles = mutableStateListOf<File>()
    var isArchiveSelectMode by mutableStateOf(false)

    init {
        loadArchiveFiles()
    }

    fun loadArchiveFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val archiveDir = File(context.filesDir, "Archive")
                if (archiveDir.exists()) {
                    val files = archiveDir.listFiles { _, name -> name.endsWith(".docx") }

                    withContext(Dispatchers.Main) {
                        archiveFiles.clear()
                        if (files != null) {
                            archiveFiles.addAll(files.sortedByDescending { it.lastModified() })
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) { archiveFiles.clear() }
                }
            } catch (e: Exception) {
                L.e("ArchiveViewModel: Помилка завантаження файлів архіву", e)
            }
        }
    }

    fun toggleArchiveFileSelection(file: File) {
        if (selectedArchiveFiles.contains(file)) {
            selectedArchiveFiles.remove(file)
            if (selectedArchiveFiles.isEmpty()) {
                isArchiveSelectMode = false
            }
        } else {
            selectedArchiveFiles.add(file)
            isArchiveSelectMode = true
        }
    }

    fun clearArchiveSelection() {
        selectedArchiveFiles.clear()
        isArchiveSelectMode = false
    }

    fun deleteSelectedArchiveFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                selectedArchiveFiles.forEach { file ->
                    if (file.exists()) {
                        val deleted = file.delete()
                        if (deleted) {
                            L.d("ArchiveViewModel: Файл ${file.name} видалено")
                        }
                    }
                }
                withContext(Dispatchers.Main) {
                    clearArchiveSelection()
                    loadArchiveFiles()
                }
            } catch (e: Exception) {
                L.e("ArchiveViewModel: Помилка при видаленні файлів", e)
            }
        }
    }
}
