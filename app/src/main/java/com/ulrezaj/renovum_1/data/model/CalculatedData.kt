package com.ulrezaj.renovum_1.data.model

data class CalculatedData(
	val floorArea: Double,
	val wallArea: Double,
	val cleanWallArea: Double,
	val allOpeningsArea: Double,
	val perimeter: Double,
	val extra: Map<String, Double>
)