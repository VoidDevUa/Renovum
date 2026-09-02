package com.void_dev_ua.renovum.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.domain.model.ReportData
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.domain.model.AppliedWork
import com.void_dev_ua.renovum.domain.model.WorkService
import com.void_dev_ua.renovum.core.util.L
import com.void_dev_ua.renovum.data.remote.RenovumNotificationManager
import com.void_dev_ua.renovum.data.remote.WordExportManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel() {

    fun generateWordReportInBackground(
        context: Context,
        isGroupedByRooms: Boolean,
        targetAddress: String,
        customFileName: String,
        userSettings: UserSettings,
        groupedWorks: Map<RoomEntity, List<Pair<AppliedWork, WorkService>>>,
        totalRawSum: Double,
        projectDiscountPercent: Double,
        totalDiscountedSum: Double
    ) {
        val appContext = context.applicationContext
        val notificationId = System.currentTimeMillis().hashCode()

        viewModelScope.launch {
            RenovumNotificationManager.showProgressNotification(appContext, notificationId)
            Toast.makeText(appContext, "Формування файлу кошторису...", Toast.LENGTH_SHORT).show()

            val wordFile = withContext(Dispatchers.IO) {
                try {
                    val locale = appContext.resources.configuration.locales[0] ?: Locale.getDefault()
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy", locale)
                    val currentDateString = dateFormat.format(Date())

                    val reportData = ReportData(
                        projectName = targetAddress,
                        dateString = currentDateString,
                        roomsWithWorks = groupedWorks,
                        totalRawSum = totalRawSum,
                        discountPercent = projectDiscountPercent,
                        totalDiscountedSum = totalDiscountedSum
                    )

                    WordExportManager.createWordDocument(
                        context = appContext,
                        data = reportData,
                        isGroupedByRooms = isGroupedByRooms,
                        customFileName = customFileName,
                        userSettings = userSettings
                    )
                } catch (e: Exception) {
                    L.e("ReportViewModel: Помилка генерації документа", e)
                    null
                }
            }

            if (wordFile != null && wordFile.exists()) {
                L.d("ReportViewModel: Фоновий файл успішно створено!")
                Toast.makeText(appContext, "Файл-кошторис створено успішно!", Toast.LENGTH_LONG).show()
                RenovumNotificationManager.showSuccessNotification(appContext, wordFile, notificationId)
            } else {
                Toast.makeText(appContext, "Не вдалося згенерувати файл", Toast.LENGTH_SHORT).show()
                RenovumNotificationManager.cancelExportNotification(appContext, notificationId)
            }
        }
    }
}
