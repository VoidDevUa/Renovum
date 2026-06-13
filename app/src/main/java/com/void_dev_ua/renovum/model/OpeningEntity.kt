package com.void_dev_ua.renovum.model

enum class OpeningType(val displayName: String) {
	WINDOW("Вікно"),
	DOOR("Двері")
}

data class OpeningEntity(
	val label: String,
	val width: String,
	val height: String,
	val type: OpeningType
)