package com.ulrezaj.renovum_1.data

import com.ulrezaj.renovum_1.ui.theme.AppTheme

data class UserSettings(
	var isLeftHanded: Boolean = false,
	var appTheme: AppTheme = AppTheme.System,
	val showDimensionsInCard: Boolean = true,
	val dialogColumns: Int = 2
)
