package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.CalculatedData
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.domain.repository.ProjectStateRepository
import com.void_dev_ua.renovum.domain.usecase.RoomCalculationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CalcScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val projectStateRepository: ProjectStateRepository,
    private val roomCalculationsUseCase: RoomCalculationsUseCase
) : ViewModel() {

    val rooms: StateFlow<List<RoomEntity>> = roomRepository.allRooms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedRoom: StateFlow<RoomEntity?> = combine(rooms, projectStateRepository.selectedRoomId) { list, id ->
        list.find { it.id == id } ?: list.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val calculationData: StateFlow<CalculatedData?> = selectedRoom.map { room ->
        room?.let { roomCalculationsUseCase.calculateRoomData(it) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun selectRoom(room: RoomEntity) {
        projectStateRepository.setSelectedRoomId(room.id)
    }
}
