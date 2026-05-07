package com.ulrezaj.renovum_1.data.model

/**
 * Базовий клас для параметрів кімнати.
 */
sealed class RoomParams {
	data class RectangleParams(
		val length: Double = 0.0,
		val width: Double = 0.0,
		val height: Double = 0.0
	) : RoomParams()

	data class LShapedParams(
		val a: Double = 0.0,
		val b: Double = 0.0,
		val c: Double = 0.0,
		val d: Double = 0.0,
		val height: Double = 0.0
	) : RoomParams()

	data class TShapedParams(
		val leftShoulder: Double,
		val rightShoulder: Double,
		val topHeight: Double,
		val legWidth: Double,
		val legHeight: Double,
		val height: Double
	) : RoomParams()

	companion object {
		private fun String?.toDoubleSafe() = this?.toDoubleOrNull() ?: 0.0

		fun fromMap(shapeType: RoomShapeType, values: Map<String, String>): RoomParams {
			return when (shapeType) {
				RoomShapeType.RECTANGLE -> RectangleParams(
					length = values["Довжина"].toDoubleSafe(),
					width = values["Ширина"].toDoubleSafe(),
					height = values["Висота"].toDoubleSafe()
				)
				RoomShapeType.L_SHAPED -> LShapedParams(
					a = values["A"].toDoubleSafe(),
					b = values["B"].toDoubleSafe(),
					c = values["C"].toDoubleSafe(),
					d = values["D"].toDoubleSafe(),
					height = values["Висота"].toDoubleSafe()
				)
				RoomShapeType.T_SHAPED -> TShapedParams(
					leftShoulder = values["Ліве плече"].toDoubleSafe(),
					rightShoulder = values["Праве плече"].toDoubleSafe(),
					topHeight = values["Висота верху"].toDoubleSafe(),
					legWidth = values["Ширина ніжки"].toDoubleSafe(),
					legHeight = values["Висота ніжки"].toDoubleSafe(),
					height = values["Висота"].toDoubleSafe()
				)
			}
		}
	}
}