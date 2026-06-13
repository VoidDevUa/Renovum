package com.void_dev_ua.renovum.model

data class ReportData(
	val projectName: String,
	val dateString: String,
	val roomsWithWorks: Map<RoomEntity, List<Pair<AppliedWork, WorkService>>>,
	val totalRawSum: Double,
	val discountPercent: Double,
	val totalDiscountedSum: Double
)