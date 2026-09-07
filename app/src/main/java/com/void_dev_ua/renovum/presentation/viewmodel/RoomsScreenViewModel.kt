package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.data.repositories.WorkRepository
import com.void_dev_ua.renovum.domain.repository.ProjectStateRepository
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val workRepository: WorkRepository,
    private val projectStateRepository: ProjectStateRepository
) : ViewModel() {

    val rooms: StateFlow<List<RoomEntity>> = roomRepository.allRooms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectRoom(room: RoomEntity) {
        projectStateRepository.setSelectedRoomId(room.id)
    }

    fun deleteRoom(room: RoomEntity) {
        viewModelScope.launch {
            workRepository.deleteWorksByRoomId(room.id)
            roomRepository.delete(room)
            L.d("RoomsScreenViewModel: Deleted room ${room.name}")
        }
    }
}
