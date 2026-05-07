package com.ulrezaj.renovum_1.data.model

import java.util.UUID

data class RoomEntity(
	val id: String = UUID.randomUUID().toString(),
	val name: String,
	val shapeType: RoomShapeType = RoomShapeType.RECTANGLE,
	val params: RoomParams,
	val openings: List<OpeningEntity> = emptyList()
)

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