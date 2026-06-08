package com.void_dev_ua.renovum.data

import com.void_dev_ua.renovum.ui.theme.AppTheme

data class UserSettings(
	val isLeftHanded: Boolean = false,
	val appTheme: AppTheme = AppTheme.SYSTEM,
	val showDimensionsInCard: Boolean = true,
	val dialogColumns: Int = 2,
	// НАЛАШТУВАННЯ WORD
	val groupWordByRooms: Boolean = true,
	val showDiscountInWord: Boolean = true,
	// ПРОФІЛЬ ТА ПОТОЧНИЙ ОБ'ЄКТ
	val masterName: String = "",
	val masterPhone: String = "",
	val currentObjectAddress: String = "" // Порожній рядок означає, що ремонт не почато
)