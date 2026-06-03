package com.ulrezaj.renovum_1.data.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.ReportData
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.utility.L
import com.ulrezaj.renovum_1.utility.RenovumFileProvider
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
	fun createWordDocument(context: Context, data: ReportData, isGroupedByRooms: Boolean): File? {
		val appContext = context.applicationContext
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
				val drawableId = appContext.applicationInfo.icon
				val drawable = ContextCompat.getDrawable(appContext, drawableId)

				val bitmap = drawable?.let { d ->
					try {
						d.toBitmap(48, 48, Bitmap.Config.ARGB_8888)
					} catch (_: Exception) {
						val bmp = createBitmap(48, 48)
						val canvas = Canvas(bmp)
						d.setBounds(0, 0, canvas.width, canvas.height)
						d.draw(canvas)
						bmp
					}
				}

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

			val archiveDir = File(appContext.filesDir, "Archive")
			if (!archiveDir.exists()) archiveDir.mkdirs()
			val archiveFile = File(archiveDir, fileName)
			FileOutputStream(archiveFile).use { out ->
				document.write(out)
			}
			L.d("WordExportManager: Файл успішно збережено в локальний архів додатка")

			RenovumFileProvider.saveToPublicDocuments(appContext, archiveFile)

			document.close()
			return archiveFile
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
	 * Форматує число до максимум 2 знаків після коми.
	 * Прибирає .00 або кінцеві нулі (наприклад, 10.50 -> 10.5, 10.00 -> 10)
	 */
	private fun formatDouble(value: Double): String {
		return String.format(Locale.US, "%.2f", value)
			.replace(".00", "")
			.replace(Regex("\\.(\\d)0$"), ".$1")
	}
}