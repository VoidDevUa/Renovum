package com.void_dev_ua.renovum.domain.usecase

import com.void_dev_ua.renovum.model.CalculatedData
import com.void_dev_ua.renovum.model.RoomEntity
import com.void_dev_ua.renovum.model.TargetSurface
import javax.inject.Inject

enum class CalculationOptionType {
    FLOOR, WALLS_CLEAN, WALLS_GROSS, PERIMETER
}

class RoomCalculationsUseCase @Inject constructor() {

    fun calculateRoomData(room: RoomEntity): CalculatedData {
        val p = room.params
        val floorArea = p.getFloorArea()
        val perimeter = p.getPerimeter()

        val openingsArea = room.openings.sumOf { it.width.toDouble() * it.height.toDouble() }
        val wallArea = (perimeter * p.roomHeight)
        val cleanWallArea = wallArea - openingsArea

        return CalculatedData(floorArea, wallArea, cleanWallArea, openingsArea, perimeter, p.getExtraResults())
    }

    fun getSurfaceValue(target: TargetSurface, calcData: CalculatedData): Double {
        return when (target) {
            TargetSurface.FLOOR_AREA -> calcData.floorArea
            TargetSurface.WALL_CLEAN_AREA -> calcData.cleanWallArea
            TargetSurface.WALL_GROSS_AREA -> calcData.wallArea
            TargetSurface.CEILING_AREA -> calcData.floorArea
            TargetSurface.ROOM_PERIMETER -> calcData.perimeter
            TargetSurface.ANY_SQUARE_METER, TargetSurface.ANY_RUNNING_METER -> 1.0
            TargetSurface.NONE -> 0.0
        }
    }

    fun getAvailableOptions(target: TargetSurface, calcData: CalculatedData): List<Pair<CalculationOptionType, Double>> {
        val options = mutableListOf<Pair<CalculationOptionType, Double>>()

        when (target) {
            TargetSurface.ANY_SQUARE_METER -> {
                options.add(CalculationOptionType.FLOOR to calcData.floorArea)
                options.add(CalculationOptionType.WALLS_CLEAN to calcData.cleanWallArea)
                options.add(CalculationOptionType.WALLS_GROSS to calcData.wallArea)
            }

            TargetSurface.ANY_RUNNING_METER -> {
                options.add(CalculationOptionType.PERIMETER to calcData.perimeter)
            }

            else -> {}
        }

        return options
    }
}
