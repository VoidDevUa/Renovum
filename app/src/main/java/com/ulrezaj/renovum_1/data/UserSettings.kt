package com.ulrezaj.renovum_1.data

import com.ulrezaj.renovum_1.ui.theme.AppTheme

data class UserSettings(
	val isLeftHanded: Boolean = false,
	val appTheme: AppTheme = AppTheme.SYSTEM,
	val showDimensionsInCard: Boolean = true,
	val dialogColumns: Int = 2,
	// НАЛАШТУВАННЯ WORD
	val groupWordByRooms: Boolean = true,
	val showDiscountInWord: Boolean = true
)