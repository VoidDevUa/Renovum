package com.void_dev_ua.renovum.data.remote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap
import com.void_dev_ua.renovum.R
import com.void_dev_ua.renovum.core.util.L
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.domain.model.AppliedWork
import com.void_dev_ua.renovum.domain.model.ReportData
import com.void_dev_ua.renovum.domain.model.WorkService
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

    /**
     * Основна функція для створення документа Word.
     */
    fun createWordDocument(
        context: Context,
        data: ReportData,
        isGroupedByRooms: Boolean,
        customFileName: String,
        userSettings: UserSettings
    ): File? {
        val appContext = context.applicationContext
        try {
            val projectName = customFileName.ifBlank { data.projectName }
            val baseFileName = "Koshtorys_${projectName.replace(" ", "_")}"
            val document = XWPFDocument()

            setupPageMargins(document)
            addHeaderAndFooter(document, appContext)
            addBrandingHeader(document, appContext, userSettings, data.projectName)
            addReportTitle(document, appContext)
            addWorksSection(document, appContext, data, isGroupedByRooms)
            addSummaryFooter(document, appContext, data, isGroupedByRooms)

            return saveDocumentToFile(document, appContext, baseFileName)
        } catch (e: Exception) {
            L.e("WordExportManager: Помилка створення документу", e)
            return null
        }
    }

    /**
     * Налаштування полів сторінки.
     */
    private fun setupPageMargins(document: XWPFDocument) {
        val documentPr = document.document.body
        val sectPr = if (documentPr.isSetSectPr) documentPr.sectPr else documentPr.addNewSectPr()
        val pageMar = if (sectPr.isSetPgMar) sectPr.pgMar else sectPr.addNewPgMar()

        pageMar.top = BigInteger.valueOf(850)    // 42.5pt
        pageMar.bottom = BigInteger.valueOf(850) // 42.5pt
        pageMar.right = BigInteger.valueOf(850)  // 42.5pt
        pageMar.left = BigInteger.valueOf(1417)  // 70.85pt
    }

    /**
     * Додавання службових колонтитулів (дисклеймер та нумерація).
     */
    private fun addHeaderAndFooter(document: XWPFDocument, context: Context) {
        // Header: Disclaimer
        val header = document.createHeader(HeaderFooterType.DEFAULT)
        header.createParagraph().apply {
            alignment = ParagraphAlignment.CENTER
        }.createRun().apply {
            isBold = true
            fontSize = 10
            fontFamily = "Arial"
            setText(context.getString(R.string.report_disclaimer))
        }

        // Footer: Page numbering
        val footer = document.createFooter(HeaderFooterType.DEFAULT)
        val footerParagraph = footer.createParagraph().apply {
            alignment = ParagraphAlignment.RIGHT
        }
        footerParagraph.createRun().apply {
            fontSize = 10
            fontFamily = "Arial"
        }
        footerParagraph.ctp.addNewFldSimple().instr = "PAGE"
    }

    /**
     * Створення шапки з логотипом та даними майстра.
     */
    private fun addBrandingHeader(
        document: XWPFDocument,
        context: Context,
        userSettings: UserSettings,
        projectName: String
    ) {
        val headerTable = document.createTable(1, 2)
        headerTable.removeBorders()

        val colWidthsHeader = intArrayOf(3000, 6933)
        val rowHeader = headerTable.getRow(0)

        // Ліва комірка: Логотип + RENOVUM
        val leftCell = rowHeader.getCell(0).apply { setWidth(colWidthsHeader[0].toString()) }
        addLogoToCell(leftCell, context)
        
        leftCell.addParagraph().apply {
            alignment = ParagraphAlignment.CENTER
            spacingAfter = 0
        }.createRun().apply {
            isBold = true; fontSize = 14; fontFamily = "Arial"; setText("RENOVUM")
        }

        // Права комірка: Дані майстра
        val rightCell = rowHeader.getCell(1).apply { setWidth(colWidthsHeader[1].toString()) }
        val notSpecified = context.getString(R.string.report_master_not_specified)
        
        val masterName = userSettings.masterName.ifBlank { notSpecified }
        val masterPhone = userSettings.masterPhone.ifBlank { notSpecified }

        val pStyle = { p: org.apache.poi.xwpf.usermodel.XWPFParagraph -> 
            p.alignment = ParagraphAlignment.RIGHT 
        }

        rightCell.paragraphs[0].apply(pStyle).createRun().apply {
            fontSize = 11; fontFamily = "Arial"
            setText(context.getString(R.string.report_master_label, masterName))
        }
        rightCell.addParagraph().apply(pStyle).createRun().apply {
            fontSize = 11; fontFamily = "Arial"
            setText(context.getString(R.string.report_phone_label, masterPhone))
        }
        rightCell.addParagraph().apply(pStyle).createRun().apply {
            fontSize = 11; fontFamily = "Arial"
            setText(context.getString(R.string.report_address_label, projectName))
        }

        document.createParagraph().spacingAfter = 240
    }

    private fun addLogoToCell(cell: org.apache.poi.xwpf.usermodel.XWPFTableCell, context: Context) {
        val imgParagraph = cell.paragraphs[0].apply {
            alignment = ParagraphAlignment.CENTER
            spacingAfter = 60
        }
        try {
            val drawableId = context.applicationInfo.icon
            val drawable = ContextCompat.getDrawable(context, drawableId)

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

                imgParagraph.createRun().addPicture(
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
    }

    /**
     * Додавання головного заголовка "КОШТОРИС РЕМОНТУ".
     */
    private fun addReportTitle(document: XWPFDocument, context: Context) {
        document.createParagraph().apply {
            alignment = ParagraphAlignment.CENTER
            spacingAfter = 240
        }.createRun().apply {
            isBold = true
            fontSize = 16
            fontFamily = "Times New Roman"
            setText(context.getString(R.string.report_title))
        }
    }

    /**
     * Секція зі списками робіт.
     */
    private fun addWorksSection(
        document: XWPFDocument,
        context: Context,
        data: ReportData,
        isGroupedByRooms: Boolean
    ) {
        if (isGroupedByRooms) {
            data.roomsWithWorks.forEach { (room, works) ->
                val roomTotal = works.sumOf { it.first.priceAtTime * it.first.quantity }

                val roomParagraph = document.createParagraph().apply {
                    spacingBefore = 300
                    spacingAfter = 120
                }

                // Табуляція для вирівнювання суми по правому краю
                val ctp = roomParagraph.ctp
                val pPr = if (ctp.isSetPPr) ctp.pPr else ctp.addNewPPr()
                val tabs = if (pPr.isSetTabs) pPr.tabs else pPr.addNewTabs()
                tabs.addNewTab().apply {
                    setVal(STTabJc.RIGHT)
                    pos = BigInteger.valueOf(9933)
                }

                roomParagraph.createRun().apply {
                    isBold = true; fontSize = 14; fontFamily = "Arial"; setText(room.name)
                }
                roomParagraph.createRun().apply {
                    isBold = true; fontSize = 14; fontFamily = "Arial"
                    setText("\t${context.getString(R.string.report_room_total, formatDouble(roomTotal))}")
                }

                buildWorksTable(context, document, works)
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
            buildWorksTable(context, document, aggregatedWorks)
        }
    }

    /**
     * Фінальний блок з підсумками.
     */
    private fun addSummaryFooter(
        document: XWPFDocument,
        context: Context,
        data: ReportData,
        isGroupedByRooms: Boolean
    ) {
        document.createParagraph().spacingBefore = 200

        val footerTable = document.createTable(1, 2).apply { removeBorders() }
        val rowFooter = footerTable.getRow(0)
        
        // Знижка
        val discountCell = rowFooter.getCell(0).apply { setWidth("5400") }
        if (isGroupedByRooms && data.discountPercent > 0) {
            discountCell.paragraphs[0].createRun().apply {
                fontSize = 12; fontFamily = "Arial"
                setText(context.getString(R.string.report_discount_label, formatDouble(data.discountPercent)))
            }
        }

        // Загальна сума
        val totalParagraph = rowFooter.getCell(1).apply { setWidth("4533") }.paragraphs[0].apply {
            alignment = ParagraphAlignment.RIGHT
        }
        totalParagraph.createRun().apply {
            isBold = true; fontSize = 14; fontFamily = "Arial"
            setText(context.getString(R.string.report_total_to_pay, formatDouble(data.totalDiscountedSum)))
        }
    }

    /**
     * Збереження документа у файл.
     */
    private fun saveDocumentToFile(document: XWPFDocument, context: Context, baseFileName: String): File {
        val archiveDir = File(context.filesDir, "Archive").apply { if (!exists()) mkdirs() }
        var archiveFile = File(archiveDir, "$baseFileName.docx")
        var counter = 1

        while (archiveFile.exists()) {
            archiveFile = File(archiveDir, "${baseFileName}_($counter).docx")
            counter++
        }

        FileOutputStream(archiveFile).use { out ->
            document.write(out)
        }

        L.d("WordExportManager: Файл збережено в архів: ${archiveFile.name}")
        document.close()
        return archiveFile
    }

    /**
     * Побудова таблиці робіт з рамками.
     */
    private fun buildWorksTable(
        context: Context,
        document: XWPFDocument,
        works: List<Pair<AppliedWork, WorkService>>
    ) {
        val table = document.createTable(works.size + 1, 4)

        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "CCCCCC")

        val colWidths = intArrayOf(5533, 1500, 1400, 1500)

        // Заповнення Шапки
        val headerRow = table.getRow(0)
        val headers = arrayOf(
            context.getString(R.string.report_col_name),
            context.getString(R.string.report_col_volume),
            context.getString(R.string.report_col_price),
            context.getString(R.string.report_col_total)
        )

        for (i in 0..3) {
            val cell = headerRow.getCell(i).apply { setWidth(colWidths[i].toString()) }
            cell.paragraphs[0].apply {
                alignment = if (i == 0) ParagraphAlignment.LEFT else ParagraphAlignment.CENTER
                spacingBefore = 60; spacingAfter = 60
            }.createRun().apply {
                isBold = true; fontSize = 11; fontFamily = "Arial"; setText(headers[i])
            }
        }

        // Заповнення Даних
        val currency = context.getString(R.string.report_currency)
        works.forEachIndexed { index, (applied, service) ->
            val row = table.getRow(index + 1)
            val totalCost = applied.priceAtTime * applied.quantity

            val rowData = arrayOf(
                service.name,
                "${formatDouble(applied.quantity)} ${service.unit.displayName}",
                "${formatDouble(applied.priceAtTime)} $currency",
                "${formatDouble(totalCost)} $currency"
            )

            for (i in 0..3) {
                val cell = row.getCell(i).apply { setWidth(colWidths[i].toString()) }
                cell.paragraphs[0].apply {
                    alignment = if (i == 0) ParagraphAlignment.LEFT else ParagraphAlignment.CENTER
                    spacingBefore = 40; spacingAfter = 40
                }.createRun().apply {
                    fontSize = 11; fontFamily = "Arial"; setText(rowData[i])
                }
            }
        }
    }

    private fun formatDouble(value: Double): String {
        return String.format(Locale.US, "%.2f", value)
            .replace(".00", "")
            .replace(Regex("\\.(\\d)0$"), ".$1")
    }
}
