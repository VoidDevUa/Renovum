package com.ulrezaj.renovum_1.data.model

import com.ulrezaj.renovum_1.utility.L

/**
 * Базовий клас для параметрів кімнати.
 */
sealed class RoomParams {
	abstract fun toMap(): Map<String, Double>
	abstract val roomHeight: Double

	data class RectangleParams(
		val length: Double = 0.0,
		val width: Double = 0.0,
		val height: Double = 0.0
	) : RoomParams() {
		override val roomHeight: Double = height
		override fun toMap() = mapOf(
			"Довжина" to length,
			"Ширина" to width
		)
	}
	data class LShapedParams(
		val a: Double = 0.0,
		val b: Double = 0.0,
		val c: Double = 0.0,
		val d: Double = 0.0,
		val height: Double = 0.0
	) : RoomParams() {
		override val roomHeight: Double = height
		override fun toMap() = mapOf(
			"A" to a,
			"B" to b,
			"C" to c,
			"D" to d
		)
	}
	data class TShapedParams(
		val leftShoulder: Double,
		val rightShoulder: Double,
		val topHeight: Double,
		val legWidth: Double,
		val legHeight: Double,
		val height: Double
	) : RoomParams() {
		override val roomHeight: Double = height
		override fun toMap() = mapOf(
			"Ліве плече" to leftShoulder,
			"Праве плече" to rightShoulder,
			"Висота верху" to topHeight,
			"Ширина ніжки" to legWidth,
			"Висота ніжки" to legHeight
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
					topHeight = values["Висота верху"].toDoubleSafe("Висота верху"),
					legWidth = values["Ширина ніжки"].toDoubleSafe("Ширина ніжки"),
					legHeight = values["Висота ніжки"].toDoubleSafe("Висота ніжки"),
					height = values["Висота"].toDoubleSafe("Висота")
				)
			}
			L.d("RoomParams: Successfully created ${params::class.simpleName}")
			return params
			}
		}
}
