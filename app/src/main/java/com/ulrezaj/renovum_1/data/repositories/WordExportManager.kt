package com.ulrezaj.renovum_1.data.repositories

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.ReportData
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.utility.L
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.common.usermodel.PictureType
import org.apache.poi.util.Units
import org.apache.poi.wp.usermodel.HeaderFooterType
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.math.BigInteger
import java.util.Locale

object WordExportManager {

	private const val CHANNEL_ID = "renovum_export_channel"
	private const val NOTIFICATION_ID = 101

	fun createWordDocument(context: Context, data: ReportData, isGroupedByRooms: Boolean): File? {
		clearOldWordCache(context)
		try {
			val fileName = "Koshtorys_${data.projectName.replace(" ", "_")}.docx"
			val document = XWPFDocument()

			// ==========================================
			// НАЛАШТУВАННЯ ПОЛІВ СТОРІНКИ (Безпечний метод POI)
			// ==========================================
			val documentPr = document.document.body
			val sectPr = if (documentPr.isSetSectPr) documentPr.sectPr else documentPr.addNewSectPr()
			val pageMar = if (sectPr.isSetPgMar) sectPr.pgMar else sectPr.addNewPgMar()

			pageMar.top = BigInteger.valueOf(850)    // 42.5pt
			pageMar.bottom = BigInteger.valueOf(850) // 42.5pt
			pageMar.right = BigInteger.valueOf(850)  // 42.5pt
			pageMar.left = BigInteger.valueOf(1417)  // 70.85pt

			// ==========================================
			// 1. КОЛОНТИТУЛИ ТА НУМЕРАЦІЯ
			// ==========================================
			val header = document.createHeader(HeaderFooterType.DEFAULT)
			val headerParagraph = header.createParagraph().apply {
				alignment = ParagraphAlignment.CENTER
			}
			headerParagraph.createRun().apply {
				isBold = true
				fontSize = 10
				fontFamily = "Arial"
				setText("ДОКУМЕНТ Є ПОПЕРЕДНІМ РОЗРАХУНКОМ ВАРТОСТІ ТА НЕ Є ФІНАНСОВИМ АКТОМ СУВОРОЇ ЗВІТНОСТІ")
			}

			val footer = document.createFooter(HeaderFooterType.DEFAULT)
			val footerParagraph = footer.createParagraph().apply {
				alignment = ParagraphAlignment.RIGHT
			}
			footerParagraph.createRun().apply {
				fontSize = 10
				fontFamily = "Arial"
			}
			footerParagraph.ctp.addNewFldSimple().instr = "PAGE"

			// ==========================================
			// 2. ШАПКА ДОКУМЕНТА (Таблиця 1х2)
			// ==========================================
			val headerTable = document.createTable(1, 2)
			headerTable.removeBorders()

			val colWidthsHeader = intArrayOf(3000, 6933)
			val rowHeader = headerTable.getRow(0)

			// Ліва комірка: RENOVUM
			val leftCell = rowHeader.getCell(0).apply {
				setWidth(colWidthsHeader[0].toString())
			}
			val imgParagraph = leftCell.paragraphs[0].apply {
				alignment = ParagraphAlignment.CENTER
				spacingAfter = 60
			}
			try {
				val drawableId = context.applicationInfo.icon
				val drawable = ContextCompat.getDrawable(context, drawableId)
				val bitmap = drawable?.toBitmap()

				if (bitmap != null) {
					val stream = ByteArrayOutputStream()
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
					val byteArray = stream.toByteArray()

					val imageRun = imgParagraph.createRun()

					imageRun.addPicture(
						ByteArrayInputStream(byteArray),
						PictureType.PNG,
						"app_logo.png",
						Units.toEMU(36.0),
						Units.toEMU(36.0)
					)
				}
			} catch (e: Exception) {
				L.e("WordExportManager: Не вдалося вставити іконку додатка", e)
			}

			val titleBrandParagraph = leftCell.addParagraph().apply {
				alignment = ParagraphAlignment.CENTER
				spacingAfter = 0
			}
			titleBrandParagraph.createRun().apply {
				isBold = true
				fontSize = 14
				fontFamily = "Arial"
				setText("RENOVUM")
			}

			// Права комірка: Дані майстра
			val rightCell = rowHeader.getCell(1).apply {
				setWidth(colWidthsHeader[1].toString())
			}

			rightCell.paragraphs[0].apply {
				alignment = ParagraphAlignment.RIGHT
				spacingAfter = 0
			}.createRun().apply {
				fontSize = 11
				fontFamily = "Arial"
				setText("Майстер: Олександрович О.О.")
			}

			rightCell.addParagraph().apply {
				alignment = ParagraphAlignment.RIGHT
				spacingAfter = 0
			}.createRun().apply {
				fontSize = 11
				fontFamily = "Arial"
				setText("Тел: +38 (097) 123-45-67")
			}

			rightCell.addParagraph().apply {
				alignment = ParagraphAlignment.RIGHT
				spacingAfter = 0
			}.createRun().apply {
				fontSize = 11
				fontFamily = "Arial"
				setText("Адреса: ${data.projectName}")
			}

			document.createParagraph().spacingAfter = 240

			// ==========================================
			// 3. ГОЛОВНИЙ ЗАГОЛОВОК
			// ==========================================
			val titleParagraph = document.createParagraph().apply {
				alignment = ParagraphAlignment.CENTER
				spacingAfter = 240
			}
			titleParagraph.createRun().apply {
				isBold = true
				fontSize = 16
				fontFamily = "Times New Roman"
				setText("КОШТОРИС РЕМОНТУ")
			}

			// ==========================================
			// 4. СПИСОК РОБІТ (З групуванням чи без)
			// ==========================================
			if (isGroupedByRooms) {
				data.roomsWithWorks.forEach { (room, works) ->
					val roomTotal = works.sumOf { it.first.priceAtTime * it.first.quantity }

					val roomParagraph = document.createParagraph().apply {
						spacingBefore = 300
						spacingAfter = 120
					}

					val ctp = roomParagraph.ctp
					val pPr = if (ctp.isSetPPr) ctp.pPr else ctp.addNewPPr()
					val tabs = if (pPr.isSetTabs) pPr.tabs else pPr.addNewTabs()
					tabs.addNewTab().apply {
						setVal(STTabJc.RIGHT)
						pos = BigInteger.valueOf(9933)
					}

					roomParagraph.createRun().apply {
						isBold = true
						fontSize = 14
						fontFamily = "Arial"
						setText(room.name)
					}
					roomParagraph.createRun().apply {
						isBold = true
						fontSize = 14
						fontFamily = "Arial"
						setText("\tЗаг. сума: ${formatDouble(roomTotal)} грн")
					}

					buildWorksTable(document, works)
				}
			} else {
				val allWorks = data.roomsWithWorks.values.flatten()

				val aggregatedWorks = allWorks.groupBy { it.second }
					.map { (service, pairs) ->
						val firstApplied = pairs.first().first
						val totalQuantity = pairs.sumOf { it.first.quantity }
						val totalCost = pairs.sumOf { it.first.priceAtTime * it.first.quantity }
						val averagePrice = if (totalQuantity > 0) totalCost / totalQuantity else 0.0

						val totalApplied = AppliedWork(
							id = firstApplied.id,
							workId = firstApplied.workId,
							roomId = "GENERAL",
							quantity = totalQuantity,
							priceAtTime = averagePrice
						)

						Pair(totalApplied, service)
					}

				buildWorksTable(document, aggregatedWorks)
			}

			// ==========================================
			// 5. НИЗ ДОКУМЕНТА (Підвал)
			// ==========================================
			document.createParagraph().spacingBefore = 200

			val footerTable = document.createTable(1, 2)
			footerTable.removeBorders()

			val rowFooter = footerTable.getRow(0)

			val discountCell = rowFooter.getCell(0).apply {
				setWidth("5400")
			}
			if (isGroupedByRooms && data.discountPercent > 0) {
				discountCell.paragraphs[0].createRun().apply {
					fontSize = 12
					fontFamily = "Arial"
					setText("Знижка: ${formatDouble(data.discountPercent)}%")
				}
			}

			val totalCell = rowFooter.getCell(1).apply {
				setWidth("4533")
			}
			val totalParagraph = totalCell.paragraphs[0].apply {
				alignment = ParagraphAlignment.RIGHT
				spacingAfter = 0
			}
			totalParagraph.createRun().apply {
				isBold = true
				fontSize = 14
				fontFamily = "Arial"
				setText("ДО ОПЛАТИ: ${formatDouble(data.totalDiscountedSum)} грн")
			}

			val cacheFile = File(context.cacheDir, fileName)
			FileOutputStream(cacheFile).use { out ->
				document.write(out)
			}
			L.d("WordExportManager: Локальний файл для ShareIntent створено успішно")

			try {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
					val contentValues = ContentValues().apply {
						put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
						put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
						put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DOCUMENTS}/Renovum")
					}

					val contentResolver = context.contentResolver
					val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

					if (uri != null) {
						contentResolver.openOutputStream(uri).use { mediaOut ->
							if (mediaOut != null) {
								document.write(mediaOut)
								L.d("WordExportManager: Копію файлу успішно збережено в Documents/Renovum")
							}
						}
					}
				} else {
					val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
					val renovumDir = File(documentsDir, "Renovum")
					if (!renovumDir.exists()) renovumDir.mkdirs()

					val publicFile = File(renovumDir, fileName)
					FileOutputStream(publicFile).use { publicOut ->
						document.write(publicOut)
					}
				}
			} catch (mediaException: Exception) {
				L.e("WordExportManager: Не вдалося зберегти копію в загальну папку Documents", mediaException)
			}

			document.close()
			return cacheFile
		} catch (e: Exception) {
			L.e("WordExportManager: Помилка створення документу", e)
			return null
		}
	}

	/**
	 * Стабільний метод створення таблиці з рамками без використання низькорівневих CT-класів
	 */
	private fun buildWorksTable(
		document: XWPFDocument,
		works: List<Pair<AppliedWork, WorkService>>
	) {
		val table = document.createTable(works.size + 1, 4)

		// Встановлюємо рамки через високорівневий API самої таблиці Apache POI
		table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
		table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
		table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
		table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
		table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
		table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")

		val colWidths = intArrayOf(5533, 1500, 1400, 1500)

		// Заповнення Шапки таблиці робіт
		val headerRow = table.getRow(0)
		val headersText = arrayOf("Назва роботи", "Об'єм", "Ціна за 1", "Заг. сума")

		for (i in 0..3) {
			val cell = headerRow.getCell(i).apply {
				setWidth(colWidths[i].toString())
			}
			val p = cell.paragraphs[0].apply {
				alignment = if (i == 0) ParagraphAlignment.LEFT else ParagraphAlignment.CENTER
				spacingBefore = 60
				spacingAfter = 60
			}
			p.createRun().apply {
				isBold = true
				fontSize = 11
				fontFamily = "Arial"
				setText(headersText[i])
			}
		}

		// Заповнення контенту робіт
		works.forEachIndexed { index, (applied, service) ->
			val row = table.getRow(index + 1)
			val totalWorkPrice = applied.priceAtTime * applied.quantity

			val pricePerUnitFormatted = formatDouble(applied.priceAtTime)
			val totalWorkPriceFormatted = formatDouble(totalWorkPrice)
			val formattedQuantity = formatDouble(applied.quantity)

			val rowData = arrayOf(
				service.name,
				"$formattedQuantity ${service.unit.displayName}",
				"$pricePerUnitFormatted грн",
				"$totalWorkPriceFormatted грн"
			)

			for (i in 0..3) {
				val cell = row.getCell(i).apply {
					setWidth(colWidths[i].toString())
				}
				val p = cell.paragraphs[0].apply {
					alignment = if (i == 0) ParagraphAlignment.LEFT else ParagraphAlignment.CENTER
					spacingBefore = 40
					spacingAfter = 40
				}
				p.createRun().apply {
					fontSize = 11
					fontFamily = "Arial"
					setText(rowData[i])
				}
			}
		}
	}

	/**
	 * Видаляє всі старі згенеровані файли кошторисів з кешу додатка
	 */
	fun clearOldWordCache(context: Context) {
		try {
			val cacheDir = context.cacheDir
			val oldFiles = cacheDir.listFiles { _, name ->
				name.startsWith("Koshtorys_") && name.endsWith(".docx")
			}
			oldFiles?.forEach { file ->
				if (file.exists()) {
					val deleted = file.delete()
					if (deleted) {
						L.d("WordExportManager: Старий кеш-файл ${file.name} видалено")
					}
				}
			}
		} catch (e: Exception) {
			L.e("WordExportManager: Помилка очищення старого файлового кешу", e)
		}
	}

	/**
	 * Створює канал сповіщень
	 * Цей метод безпечно викликати щоразу перед показом — якщо канал є, система його не дублюватиме.
	 */
	private fun createNotificationChannel(context: Context) {
		val name = "Експорт кошторисів"
		val descriptionText = "Сповіщення про стан створення файлів кошторису"
		val importance = NotificationManager.IMPORTANCE_DEFAULT
		val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
			description = descriptionText
		}
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.createNotificationChannel(channel)
	}

	/**
	 * Показує початкове сповіщення, яке не можна змахнути (іде процес)
	 */
	fun showProgressNotification(context: Context) {
		createNotificationChannel(context)

		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

		val notification = NotificationCompat.Builder(context, CHANNEL_ID)
			.setSmallIcon(android.R.drawable.stat_sys_download)
			.setContentTitle("Формування кошторису")
			.setContentText("Будь ласка, зачекайте, файл створюється...")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setOngoing(true)
			.setAutoCancel(false)
			.build()

		notificationManager.notify(NOTIFICATION_ID, notification)
	}

	/**
	 * Оновлює сповіщення на фінальне (успіх), додає клац для відкриття файлу
	 */
	fun showSuccessNotification(context: Context, file: File) {
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

		val fileUri: Uri = FileProvider.getUriForFile(
			context,
			"${context.packageName}.fileprovider",
			file
		)

		val openFileIntent = Intent(Intent.ACTION_VIEW).apply {
			setDataAndType(fileUri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
			addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		}

		val pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

		val contentIntent = PendingIntent.getActivity(
			context,
			0,
			openFileIntent,
			pendingIntentFlags
		)

		val notification = NotificationCompat.Builder(context, CHANNEL_ID)
			.setSmallIcon(android.R.drawable.stat_sys_download_done)
			.setContentTitle("Кошторис створено!")
			.setContentText("Натисніть, щоб відкрити файл")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setOngoing(false)
			.setAutoCancel(true)
			.setContentIntent(contentIntent)
			.build()

		notificationManager.notify(NOTIFICATION_ID, notification)
	}

	/**
	 * Скасовує сповіщення (якщо сталася помилка і треба його просто прибрати)
	 */
	fun cancelNotification(context: Context) {
		try {
			val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.cancel(NOTIFICATION_ID)
		} catch (e: Exception) {
			L.e("WordExportManager: Не вдалося скасувати сповіщення", e)
		}
	}

	/**
	 * Форматує число до максимум 2 знаків після коми.
	 * Прибирає .00 або кінцеві нулі (наприклад, 10.50 -> 10.5, 10.00 -> 10)
	 */
	private fun formatDouble(value: Double): String {
		return String.format(Locale.US, "%.2f", value)
			.replace(".00", "")
			.replace(Regex("\\.(\\d)0$"), ".$1")
	}
}