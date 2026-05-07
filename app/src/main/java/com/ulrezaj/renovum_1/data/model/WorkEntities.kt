package com.ulrezaj.renovum_1.data.model

import java.util.UUID

enum class WorkUnit(val displayName: String) {
	M2("м²"),
	MPOG("м.пог."),
	PCS("шт."),
	POINT("точ."),
	JOB("роб.");

	companion object {
		fun fromString(unit: String): WorkUnit {
			return when (unit.lowercase()) {
				"м2", "м²" -> M2
				"м.пог.", "мп" -> MPOG
				"шт.", "шт" -> PCS
				"точ.", "точка" -> POINT
				else -> JOB
			}
		}
	}
}

data class WorkService(
	val id: String = UUID.randomUUID().toString(),
	val section: String,
	val category: String,
	val name: String,
	val unit: WorkUnit,
	val averagePrice: Double
)

data class AppliedWork(
	val id: String = UUID.randomUUID().toString(),
	val workId: String,
	val roomId: String,
	val quantity: Double,
	val priceAtTime: Double
)