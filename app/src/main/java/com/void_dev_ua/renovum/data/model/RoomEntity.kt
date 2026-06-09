package com.void_dev_ua.renovum.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "rooms")
data class RoomEntity(
	@PrimaryKey
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