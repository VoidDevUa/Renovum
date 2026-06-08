package com.void_dev_ua.renovum.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
	object Rooms : Screen("rooms", "Кімнати", Icons.Default.Home)
	object Calculations : Screen("calc", "Виміри", Icons.Default.Calculate)
	object Works : Screen("works", "Роботи", Icons.AutoMirrored.Filled.List)
	object Done : Screen("done", "Виконано", Icons.Default.DoneAll)
	object Settings : Screen("settings", "Налаштування", Icons.Default.Settings)
	object Materials : Screen("materials", "Орієнтовний список матеріалів", Icons.Default.Inventory)
	object Archive : Screen("archive", "Архів кошторисів", Icons.Default.FolderOpen)
	object Ceiling : Screen("ceiling", "Схема кріплення гіпсокартону", Icons.Default.GridOn)
	object About : Screen("about", "Про додаток", Icons.Default.Info)
	object AddRoom : Screen("add_room", "Додавання кімнати", Icons.Default.Add)
	object EditRoom : Screen("edit_room", "Редагування", Icons.Default.Edit)

	companion object {
		val allScreens = listOf(
			Rooms,
			Calculations,
			Works,
			Done,
			Settings,
			Materials,
			Archive,
			Ceiling,
			About,
			AddRoom,
			EditRoom
		)
	}
}