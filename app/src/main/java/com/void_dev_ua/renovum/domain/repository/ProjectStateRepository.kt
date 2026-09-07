package com.void_dev_ua.renovum.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectStateRepository @Inject constructor() {
    private val _selectedRoomId = MutableStateFlow<String?>(null)
    val selectedRoomId: StateFlow<String?> = _selectedRoomId.asStateFlow()

    private val _globalDiscount = MutableStateFlow(0.0)
    val globalDiscount: StateFlow<Double> = _globalDiscount.asStateFlow()

    private val _shouldShowDiscountDialog = MutableStateFlow(false)
    val shouldShowDiscountDialog: StateFlow<Boolean> = _shouldShowDiscountDialog.asStateFlow()

    fun setSelectedRoomId(roomId: String?) {
        _selectedRoomId.value = roomId
    }

    fun setGlobalDiscount(discount: Double) {
        _globalDiscount.value = discount.coerceIn(0.0, 100.0)
    }

    fun setShowDiscountDialog(show: Boolean) {
        _shouldShowDiscountDialog.value = show
    }
}
