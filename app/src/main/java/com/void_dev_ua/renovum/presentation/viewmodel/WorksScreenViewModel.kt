package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.AppliedWork
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.domain.model.WorkCategory
import com.void_dev_ua.renovum.domain.model.WorkSection
import com.void_dev_ua.renovum.domain.model.WorkService
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.data.repositories.WorkDataRepository
import com.void_dev_ua.renovum.data.repositories.WorkRepository
import com.void_dev_ua.renovum.domain.repository.ProjectStateRepository
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorksScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val workRepository: WorkRepository,
    private val workDataRepository: WorkDataRepository,
    private val projectStateRepository: ProjectStateRepository
) : ViewModel() {

    val rooms: StateFlow<List<RoomEntity>> = roomRepository.allRooms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedRoom: StateFlow<RoomEntity?> = combine(rooms, projectStateRepository.selectedRoomId) { list, id ->
        list.find { it.id == id } ?: list.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val appliedWorks = workRepository.allWorks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val worksWithStatusState: StateFlow<Map<String, Boolean>> = combine(appliedWorks, selectedRoom) { applied, room ->
        val currentRoomId = room?.id ?: ""
        applied.filter { it.roomId == currentRoomId }
            .associate { it.workId to true }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val allWorksFlow = workDataRepository.allWorksFlow
    
    private val _selectedCategory = MutableStateFlow<WorkCategory?>(null)
    val selectedCategory: StateFlow<WorkCategory?> = _selectedCategory.asStateFlow()

    val filteredWorks: StateFlow<List<WorkService>> = combine(allWorksFlow, _selectedCategory) { works, category ->
        if (category == null) emptyList()
        else works.filter { it.category == category }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            workDataRepository.loadWorks()
            // Set default category if none selected
            if (_selectedCategory.value == null) {
                _selectedCategory.value = WorkCategory.TILING
            }
        }
    }

    fun selectCategory(category: WorkCategory) {
        _selectedCategory.value = category
    }

    fun getAllSections() = workDataRepository.allSections
    fun getCategoriesForSection(section: WorkSection) = workDataRepository.getCategoriesForSection(section)

    fun saveAppliedWork(room: RoomEntity, work: WorkService, price: Double, quantity: Double) {
        val newWork = AppliedWork(
            workId = work.id,
            roomId = room.id,
            quantity = quantity,
            priceAtTime = price
        )
        viewModelScope.launch {
            workRepository.insert(newWork)
            L.d("WorksScreenViewModel: Saved ${work.name} to DB for ${room.name}")
        }
    }
}
