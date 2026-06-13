package com.void_dev_ua.renovum.model

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