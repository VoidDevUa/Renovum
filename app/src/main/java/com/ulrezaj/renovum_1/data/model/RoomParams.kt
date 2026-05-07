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
		val topWidth: Double = 0.0,
		val topHeight: Double = 0.0,
		val legWidth: Double = 0.0,
		val legHeight: Double = 0.0,
		val height: Double = 0.0
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
					topWidth = values["Ширина верху"].toDoubleSafe(),
					topHeight = values["Висота верху"].toDoubleSafe(),
					legWidth = values["Ширина ніжки"].toDoubleSafe(),
					legHeight = values["Висота ніжки"].toDoubleSafe(),
					height = values["Висота"].toDoubleSafe()
				)
			}
		}
	}
}