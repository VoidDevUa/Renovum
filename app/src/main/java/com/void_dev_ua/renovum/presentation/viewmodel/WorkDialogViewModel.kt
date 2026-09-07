package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.void_dev_ua.renovum.domain.model.CalculatedData
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.domain.model.TargetSurface
import com.void_dev_ua.renovum.domain.usecase.CalculationOptionType
import com.void_dev_ua.renovum.domain.usecase.RoomCalculationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkDialogViewModel @Inject constructor(
    private val roomCalculationsUseCase: RoomCalculationsUseCase
) : ViewModel() {

    fun calculateRoomData(room: RoomEntity): CalculatedData {
        return roomCalculationsUseCase.calculateRoomData(room)
    }

    fun getSurfaceValue(target: TargetSurface, calcData: CalculatedData): Double {
        return roomCalculationsUseCase.getSurfaceValue(target, calcData)
    }

    fun getAvailableOptions(target: TargetSurface, calcData: CalculatedData): List<Pair<CalculationOptionType, Double>> {
        return roomCalculationsUseCase.getAvailableOptions(target, calcData)
    }
}
