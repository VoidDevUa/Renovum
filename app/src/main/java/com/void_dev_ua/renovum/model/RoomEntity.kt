package com.void_dev_ua.renovum.model

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