package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.domain.repository.ProjectStateRepository
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRoomScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val projectStateRepository: ProjectStateRepository
) : ViewModel() {

    fun addRoom(room: RoomEntity) {
        viewModelScope.launch {
            roomRepository.insert(room)
            projectStateRepository.setSelectedRoomId(room.id)
            L.d("AddRoomScreenViewModel: Added room ${room.name}")
        }
    }
}
