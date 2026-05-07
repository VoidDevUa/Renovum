package com.ulrezaj.renovum_1.ui.components

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.model.RoomShapeType
import com.ulrezaj.renovum_1.utility.L

@SuppressLint("DefaultLocale")
@Composable
fun RoomSchemaPainter(
	shapeType: RoomShapeType,
	focusedField: String?,
	paramValues: Map<String, Double>,
	modifier: Modifier = Modifier
) {
	LaunchedEffect(shapeType) {
		L.d("Painter: Shape changed to $shapeType")
	}

	LaunchedEffect(focusedField) {
		if (focusedField != null) {
			L.d("Painter: Highlighting field '$focusedField'")
		}
	}

	val outlineColor = MaterialTheme.colorScheme.outline
	val highlightColor = MaterialTheme.colorScheme.tertiary
	val strokeWidth = 4.dp

	val textPaint = Paint().apply {
		color = android.graphics.Color.GRAY
		textAlign = Paint.Align.CENTER
		textSize = 32f
		typeface = Typeface.DEFAULT_BOLD
	}

	Box(
		modifier = modifier.fillMaxWidth().height(260.dp).padding(24.dp),
		contentAlignment = Alignment.Center
	) {
		Canvas(modifier = Modifier.fillMaxSize()) {
			val canvasW = size.width
			val canvasH = size.height
			val safePadding = 60f

			fun getParam(key: String, def: Double = 4.0): Double = paramValues[key] ?: def
			fun getCol(field: String): Color = if (field == focusedField) highlightColor else outlineColor

			when (shapeType) {
				RoomShapeType.RECTANGLE -> {
					val mW = getParam("Ширина", 4.0)
					val mL = getParam("Довжина", 5.0)
					val scale = minOf((canvasW - safePadding * 2) / mW, (canvasH - safePadding * 2) / mL)
					val dW = (mW * scale).toFloat()
					val dL = (mL * scale).toFloat()
					val x0 = (canvasW - dW) / 2
					val y0 = (canvasH - dL) / 2

					val p1 = Offset(x0, y0); val p2 = Offset(x0 + dW, y0)
					val p3 = Offset(x0 + dW, y0 + dL); val p4 = Offset(x0, y0 + dL)

					drawLine(getCol("Ширина"), p1, p2, strokeWidth.toPx())
					drawLine(getCol("Довжина"), p2, p3, strokeWidth.toPx())
					drawLine(getCol("Ширина"), p3, p4, strokeWidth.toPx())
					drawLine(getCol("Довжина"), p4, p1, strokeWidth.toPx())

					drawContext.canvas.nativeCanvas.drawText("${mW}м", (p1.x + p2.x) / 2, p1.y - 20f, textPaint)
					drawContext.canvas.nativeCanvas.drawText("${mL}м", p2.x + 55f, (p2.y + p3.y) / 2, textPaint)
				}
				RoomShapeType.L_SHAPED -> {
					val a = getParam("A", 6.0)
					val b = getParam("B", 3.0)
					val c = minOf(getParam("C", 3.0), a * 0.8)
					val d = maxOf(getParam("D", 6.0), b + 0.5)

					val scale = minOf((canvasW - safePadding * 2) / a, (canvasH - safePadding * 2) / d).toFloat()
					val x0 = (canvasW - (a.toFloat() * scale)) / 2
					val y0 = (canvasH - (d.toFloat() * scale)) / 2

					val p1 = Offset(x0, y0)
					val p2 = Offset(x0 + a.toFloat() * scale, y0)
					val p3 = Offset(x0 + a.toFloat() * scale, y0 + b.toFloat() * scale)
					val p4 = Offset(x0 + (a - c).toFloat() * scale, y0 + b.toFloat() * scale)
					val p5 = Offset(x0 + (a - c).toFloat() * scale, y0 + d.toFloat() * scale)
					val p6 = Offset(x0, y0 + d.toFloat() * scale)

					val bottomWall = a - c
					val innerVertical = d - b

					drawLine(getCol("A"), p1, p2, strokeWidth.toPx())
					drawLine(getCol("B"), p2, p3, strokeWidth.toPx())
					drawLine(getCol("C"), p3, p4, strokeWidth.toPx())
					drawLine(outlineColor, p4, p5, strokeWidth.toPx())
					drawLine(outlineColor, p5, p6, strokeWidth.toPx())
					drawLine(getCol("D"), p6, p1, strokeWidth.toPx())

					drawContext.canvas.nativeCanvas.drawText("${a}м", (p1.x + p2.x)/2, p1.y - 20f, textPaint)
					drawContext.canvas.nativeCanvas.drawText("${b}м", p2.x + 60f, (p2.y + p3.y)/2, textPaint)
					drawContext.canvas.nativeCanvas.drawText("${c}м", (p3.x + p4.x)/2, p3.y + 40f, textPaint)
					drawContext.canvas.nativeCanvas.drawText("${d}м", p1.x - 60f, (p1.y + p6.y)/2, textPaint)
					drawContext.canvas.nativeCanvas.drawText("${bottomWall}м", (p5.x + p6.x) / 2, p5.y + 40f, textPaint)
					drawContext.canvas.nativeCanvas.drawText("${innerVertical}м", p5.x + 60f, (p4.y + p5.y) / 2, textPaint)
				}
				RoomShapeType.T_SHAPED -> {
					val pL = getParam("Ліве плече", 2.0)
					val pR = getParam("Праве плече", 2.0)
					val pLW = getParam("Ширина ніжки", 2.0)
					val pLLH = getParam("Ліва ніжка", 3.0)
					val pRLH = getParam("Права ніжка", 3.0)
					val pTHL = getParam("Висота верху (ліво)", 2.0)

					val pTHRRaw = pTHL + pLLH - pRLH
					val pTHR = maxOf(pTHRRaw, 0.1)

					val effectiveRLH = if (pTHRRaw < 0.1) pTHL + pLLH - 0.1 else pRLH

					val tW = pL + pLW + pR
					val tH = maxOf(pTHL + pLLH, pTHR + effectiveRLH)

					val scale = minOf((canvasW - safePadding * 2) / tW, (canvasH - safePadding * 2) / tH).toFloat()
					val x0 = (canvasW - tW.toFloat() * scale) / 2
					val y0 = (canvasH - tH.toFloat() * scale) / 2

					val tp1 = Offset(x0, y0)
					val tp2 = Offset(x0 + tW.toFloat() * scale, y0)
					val tp3 = Offset(x0 + tW.toFloat() * scale, y0 + pTHR.toFloat() * scale)
					val tp4 = Offset(x0 + (pL + pLW).toFloat() * scale, y0 + pTHR.toFloat() * scale)
					val tp5 = Offset(x0 + (pL + pLW).toFloat() * scale, y0 + (pTHR + effectiveRLH).toFloat() * scale)
					val tp6 = Offset(x0 + pL.toFloat() * scale, y0 + (pTHL + pLLH).toFloat() * scale)
					val tp7 = Offset(x0 + pL.toFloat() * scale, y0 + pTHL.toFloat() * scale)
					val tp8 = Offset(x0, y0 + pTHL.toFloat() * scale)

					drawLine(outlineColor, tp1, tp2, strokeWidth.toPx())
					drawLine(getCol("Ліве плече"), tp8, tp7, strokeWidth.toPx())
					drawLine(getCol("Праве плече"), tp4, tp3, strokeWidth.toPx())

					val thLCol = getCol("Висота верху (ліво)")
					drawLine(thLCol, tp1, tp8, strokeWidth.toPx())
					val thRCol = if (pTHRRaw < 0.1) Color.Red else outlineColor
					drawLine(thRCol, tp2, tp3, strokeWidth.toPx())

					drawLine(getCol("Ширина ніжки"), tp5, tp6, strokeWidth.toPx())
					drawLine(getCol("Ліва ніжка"), tp6, tp7, strokeWidth.toPx())
					drawLine(getCol("Права ніжка"), tp4, tp5, strokeWidth.toPx())

					val canvas = drawContext.canvas.nativeCanvas
					fun formatDim(value: Double): String = String.format("%.1fм", value)

					canvas.drawText(formatDim(tW), (tp1.x + tp2.x) / 2, tp1.y - 35f, textPaint)
					canvas.drawText(formatDim(pTHL), tp1.x - 45f, (tp1.y + tp8.y) / 2 + 10f, textPaint)
					canvas.drawText(formatDim(pL), (tp8.x + tp7.x) / 2, tp8.y + 40f, textPaint)
					canvas.drawText(formatDim(pLLH), tp7.x - 45f, (tp7.y + tp6.y) / 2 + 10f, textPaint)
					canvas.drawText(formatDim(pLW), (tp6.x + tp5.x) / 2, tp6.y + 50f, textPaint)
					canvas.drawText(formatDim(pRLH), tp5.x + 45f, (tp5.y + tp4.y) / 2 + 10f, textPaint)
					canvas.drawText(formatDim(pR), (tp4.x + tp3.x) / 2, tp4.y + 40f, textPaint)

					val thrPaint = if (pTHRRaw < 0.1) {
						Paint(textPaint).apply { color = android.graphics.Color.RED }
					} else textPaint

					val formattedTHR = String.format("%.2f", pTHRRaw)
					canvas.drawText("${formattedTHR}м", tp2.x + 60f, (tp2.y + tp3.y) / 2 + 10f, thrPaint)
				}
			}
		}
	}
}