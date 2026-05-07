package com.ulrezaj.renovum_1.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
	object Rooms : Screen("rooms", "Кімнати", Icons.Default.Home)
	object Calculations : Screen("calc", "Виміри", Icons.Default.Calculate)
	object Jobs : Screen("jobs", "Роботи", Icons.AutoMirrored.Filled.List)
	object Done : Screen("done", "Виконано", Icons.Default.DoneAll)
	object Settings : Screen("settings", "Налаштування", Icons.Default.Settings)
	object Materials : Screen("materials", "Орієнтовний список матеріалів", Icons.Default.Inventory)
	object Ceiling : Screen("ceiling", "Схема кріплення гіпсокартону", Icons.Default.GridOn)
	object About : Screen("about", "Про додаток", Icons.Default.Info)
	object AddRoom : Screen("add_room", "Додавання кімнати", Icons.Default.Add)
	object EditRoom : Screen("edit_room/{roomId}", "Редагування", Icons.Default.Edit) {
		fun createRoute(roomId: String) = "edit_room/$roomId"
	}

	companion object {
		val allScreens = listOf(
			Rooms,
			Calculations,
			Jobs,
			Done,
			Settings,
			Materials,
			Ceiling,
			About,
			AddRoom
		)
	}
}