package com.void_dev_ua.renovum.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.void_dev_ua.renovum.data.model.OpeningEntity
import com.void_dev_ua.renovum.data.model.RoomParams

class Converters {
	private val gson = Gson()

	@TypeConverter
	fun fromOpeningsList(value: List<OpeningEntity>?): String {
		return gson.toJson(value ?: emptyList<OpeningEntity>())
	}

	@TypeConverter
	fun toOpeningsList(value: String?): List<OpeningEntity> {
		if (value.isNullOrEmpty()) return emptyList()
		val listType = object : TypeToken<List<OpeningEntity>>() {}.type
		return gson.fromJson(value, listType)
	}

	@TypeConverter
	fun fromRoomParams(params: RoomParams?): String {
		if (params == null) return ""
		val jsonObject = gson.toJsonTree(params).asJsonObject
		jsonObject.addProperty("type_class", params::class.java.name)
		return jsonObject.toString()
	}

	@TypeConverter
	fun toRoomParams(value: String?): RoomParams {
		if (value.isNullOrEmpty()) return RoomParams.RectangleParams()
		try {
			val jsonObject = gson.fromJson(value, JsonObject::class.java)
			val className = jsonObject.get("type_class")?.asString
				?: return gson.fromJson(value, RoomParams.RectangleParams::class.java)

			val clazz = Class.forName(className)
			return gson.fromJson(value, clazz) as RoomParams
		} catch (e: Exception) {
			com.void_dev_ua.renovum.utility.L.e("Converters: Error parsing RoomParams JSON: ${e.message}")
			return RoomParams.RectangleParams()
		}
	}
}