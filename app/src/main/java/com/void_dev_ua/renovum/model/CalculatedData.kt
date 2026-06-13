package com.void_dev_ua.renovum.model

data class CalculatedData(
	val floorArea: Double,
	val wallArea: Double,
	val cleanWallArea: Double,
	val allOpeningsArea: Double,
	val perimeter: Double,
	val extra: Map<String, Double>
)