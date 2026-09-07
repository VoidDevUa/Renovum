package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.domain.repository.ProjectStateRepository
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRoomScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val projectStateRepository: ProjectStateRepository
) : ViewModel() {

    private val rooms: StateFlow<List<RoomEntity>> = roomRepository.allRooms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedRoom: StateFlow<RoomEntity?> = combine(rooms, projectStateRepository.selectedRoomId) { list, id ->
        list.find { it.id == id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updateRoom(room: RoomEntity) {
        viewModelScope.launch {
            roomRepository.insert(room) // Room uses insert with Replace strategy for updates
            L.d("EditRoomScreenViewModel: Updated room ${room.name}")
        }
    }
}
