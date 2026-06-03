package com.ulrezaj.renovum_1.utility

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
	private const val EXPORT_NOTIFICATION_ID = 101

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
	 * Показує сповіщення про те, що йде процес генерації
	 */
	fun showProgressNotification(context: Context) {
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

		notificationManager.notify(EXPORT_NOTIFICATION_ID, notification)
	}

	/**
	 * Оновлює сповіщення на успішне завершення з можливістю кліку для відкриття файлу
	 */
	fun showSuccessNotification(context: Context, file: File) {
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
			0,
			openFileIntent,
			pendingIntentFlags
		)

		val notification = NotificationCompat.Builder(appContext, EXPORT_CHANNEL_ID)
			.setSmallIcon(android.R.drawable.stat_sys_download_done)
			.setContentTitle("Кошторис створено!")
			.setContentText("Натисніть, щоб відкрити файл")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setOngoing(false)
			.setAutoCancel(true)
			.setContentIntent(contentIntent)
			.build()

		notificationManager.notify(EXPORT_NOTIFICATION_ID, notification)
	}

	/**
	 * Скасовує сповіщення процесу експорту
	 */
	fun cancelExportNotification(context: Context) {
		try {
			val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.cancel(EXPORT_NOTIFICATION_ID)
		} catch (e: Exception) {
			L.e("RenovumNotificationManager: Не вдалося скасувати сповіщення", e)
		}
	}
}