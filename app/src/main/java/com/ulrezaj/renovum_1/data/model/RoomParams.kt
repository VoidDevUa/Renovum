package com.ulrezaj.renovum_1.data.model

import com.ulrezaj.renovum_1.utility.L

/**
 * Базовий клас для параметрів кімнати.
 */
sealed class RoomParams {
	abstract fun toMap(): Map<String, Double>
	abstract val roomHeight: Double

	abstract fun getFloorArea(): Double
	abstract fun getPerimeter(): Double

	open fun getExtraResults(): Map<String, Double> = emptyMap()

	data class RectangleParams(
		val length: Double = 0.0,
		val width: Double = 0.0,
		val height: Double = 0.0
	) : RoomParams()
	{
		override val roomHeight: Double = height
		override fun toMap() = mapOf(
			"Довжина" to length,
			"Ширина" to width
		)

		override fun getFloorArea() = length * width
		override fun getPerimeter() = 2 * (length + width)
	}
	data class LShapedParams(
		val a: Double = 0.0,
		val b: Double = 0.0,
		val c: Double = 0.0,
		val d: Double = 0.0,
		val height: Double = 0.0
	) : RoomParams()
	{
		override val roomHeight: Double = height
		override fun toMap() = mapOf(
			"A" to a,
			"B" to b,
			"C" to c,
			"D" to d
		)

		override fun getFloorArea() = (a * b) + (c * (d - b))
		override fun getPerimeter() = 2 * (a + d)

		override fun getExtraResults() = mapOf(
			"Площа основи" to (a * b),
			"Площа виступу" to (c * (d - b))
		)
	}
	data class TShapedParams(
		val leftShoulder: Double,
		val rightShoulder: Double,
		val topHeightLeft: Double,
		val legWidth: Double,
		val leftLegHeight: Double,
		val rightLegHeight: Double,
		val height: Double
	) : RoomParams()
	{
		private val topHeightRight: Double
			get() = topHeightLeft + leftLegHeight - rightLegHeight
		private val topWidth = leftShoulder + legWidth + rightShoulder
		override val roomHeight: Double = height

		override fun toMap() = mapOf(
			"Ліве плече" to leftShoulder,
			"Ширина ніжки" to legWidth,
			"Праве плече" to rightShoulder,
			"Висота верху (ліво)" to topHeightLeft,
			"Висота верху (право)" to topHeightRight,
			"Ліва ніжка" to leftLegHeight,
			"Права ніжка" to rightLegHeight
		)

		override fun getFloorArea(): Double {
			val leftPart = leftShoulder * topHeightLeft
			val middlePart = legWidth * (topHeightLeft + leftLegHeight)
			val rightPart = rightShoulder * topHeightRight
			return leftPart + middlePart + rightPart
		}
		override fun getPerimeter(): Double {
			val topWidth = leftShoulder + legWidth + rightShoulder
			return topWidth + topHeightLeft + leftShoulder + leftLegHeight +
					legWidth + rightLegHeight + rightShoulder + topHeightRight
		}

		override fun getExtraResults() = mapOf(
			"Ширина верху" to topWidth
		)
	}

	companion object {
		private fun String?.toDoubleSafe(key: String): Double {
			val value = this?.toDoubleOrNull()
			if (this != null && value == null) {
				L.e("RoomParams: Failed to parse field '$key' with value '$this'. Using 0.0")
			}
			return value ?: 0.0
		}

		fun fromMap(shapeType: RoomShapeType, values: Map<String, String>): RoomParams {
			L.d("RoomParams: Parsing from map for shape $shapeType. Raw values: $values")
			val params = when (shapeType) {
				RoomShapeType.RECTANGLE -> RectangleParams(
					length = values["Довжина"].toDoubleSafe("Довжина"),
					width = values["Ширина"].toDoubleSafe("Ширина"),
					height = values["Висота"].toDoubleSafe("Висота")
				)
				RoomShapeType.L_SHAPED -> LShapedParams(
					a = values["A"].toDoubleSafe("A"),
					b = values["B"].toDoubleSafe("B"),
					c = values["C"].toDoubleSafe("C"),
					d = values["D"].toDoubleSafe("D"),
					height = values["Висота"].toDoubleSafe("Висота")
				)
				RoomShapeType.T_SHAPED -> TShapedParams(
					leftShoulder = values["Ліве плече"].toDoubleSafe("Ліве плече"),
					rightShoulder = values["Праве плече"].toDoubleSafe("Праве плече"),
					topHeightLeft = values["Висота верху (ліво)"].toDoubleSafe("Висота верху (ліво)"),
					legWidth = values["Ширина ніжки"].toDoubleSafe("Ширина ніжки"),
					leftLegHeight = values["Ліва ніжка"].toDoubleSafe("Ліва ніжка"),
					rightLegHeight = values["Права ніжка"].toDoubleSafe("Права ніжка"),
					height = values["Висота"].toDoubleSafe("Висота")
				)
			}
			L.d("RoomParams: Successfully created ${params::class.simpleName}")
			return params
		}
	}
}
