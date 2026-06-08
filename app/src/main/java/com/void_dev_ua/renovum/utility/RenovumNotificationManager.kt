package com.void_dev_ua.renovum.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import java.io.File

object RenovumNotificationManager {
	private const val EXPORT_CHANNEL_ID = "renovum_export_channel"

	/**
	 * Створює канал сповіщень для експорту (безпечно викликати повторно)
	 */
	private fun createExportChannel(context: Context) {
		val name = "Експорт кошторисів"
		val descriptionText = "Сповіщення про стан створення файлів кошторису"
		val importance = NotificationManager.IMPORTANCE_DEFAULT
		val channel = NotificationChannel(EXPORT_CHANNEL_ID, name, importance).apply {
			description = descriptionText
		}
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.createNotificationChannel(channel)
	}

	/**
	 * Показує сповіщення про хід генерації та повертає його унікальний ID
	 */
	fun showProgressNotification(context: Context, notificationId: Int) {
		val appContext = context.applicationContext
		createExportChannel(appContext)

		val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

		val notification = NotificationCompat.Builder(appContext, EXPORT_CHANNEL_ID)
			.setSmallIcon(android.R.drawable.stat_sys_download)
			.setContentTitle("Формування кошторису")
			.setContentText("Будь ласка, зачекайте, файл створюється...")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setOngoing(true)
			.setAutoCancel(false)
			.build()

		notificationManager.notify(notificationId, notification)
	}

	/**
	 * Оновлює сповіщення за ID на успішне завершення з можливістю кліку для відкриття файлу
	 */
	fun showSuccessNotification(context: Context, file: File, notificationId: Int) {
		val appContext = context.applicationContext
		val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

		val fileUri: Uri = FileProvider.getUriForFile(
			appContext,
			"${appContext.packageName}.fileprovider",
			file
		)

		val openFileIntent = Intent(Intent.ACTION_VIEW).apply {
			setDataAndType(fileUri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
			addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		}

		val pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

		val contentIntent = PendingIntent.getActivity(
			appContext,
			notificationId,
			openFileIntent,
			pendingIntentFlags
		)

		val notification = NotificationCompat.Builder(appContext, EXPORT_CHANNEL_ID)
			.setSmallIcon(android.R.drawable.stat_sys_download_done)
			.setContentTitle("Кошторис створено!")
			.setContentText("Натисніть, щоб відкрити ${file.name}")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setOngoing(false)
			.setAutoCancel(true)
			.setContentIntent(contentIntent)
			.build()

		notificationManager.notify(notificationId, notification)
	}

	/**
	 * Скасовує сповіщення процесу експорту за ID
	 */
	fun cancelExportNotification(context: Context, notificationId: Int) {
		try {
			val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.cancel(notificationId)
		} catch (e: Exception) {
			L.e("RenovumNotificationManager: Не вдалося скасувати сповіщення", e)
		}
	}
}