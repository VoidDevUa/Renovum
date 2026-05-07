package com.ulrezaj.renovum_1.data.model

enum class RoomShapeType {
	RECTANGLE,
	L_SHAPED,
	T_SHAPED;

	fun getDisplayName(): String {
		return when (this) {
			RECTANGLE -> "Прямокутна"
			L_SHAPED -> "Г-подібна"
			T_SHAPED -> "Т-подібна"
		}
	}
}