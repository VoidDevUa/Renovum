package com.ulrezaj.renovum_1.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.navigation.Screen

@Composable
fun BottomNav(
	isLeftHanded: Boolean,
	currentRoute: String?,
	onNavigate: (String) -> Unit,
	onMenuClick: () -> Unit
) {
	val screens = listOf(
		Screen.Rooms,
		Screen.Calculations,
		Screen.Jobs,
		Screen.Done
	)

	NavigationBar (
		containerColor = MaterialTheme.colorScheme.surface,
		tonalElevation = 0.dp
	){
		if (isLeftHanded) {
			NavigationBarItem(
				icon = { Icon(Icons.Default.Menu, contentDescription = "Меню") },
				label = { Text("Меню") },
				selected = false,
				onClick = onMenuClick
			)
		}

		screens.forEach { screen ->
			NavigationBarItem(
				icon = { Icon(screen.icon, contentDescription = screen.title) },
				label = { Text(screen.title) },
				selected = currentRoute == screen.route,
				onClick = { onNavigate(screen.route) },
				colors = NavigationBarItemDefaults.colors(
					indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
					selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
					selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
					unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
					unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
				)
			)
		}

		if (!isLeftHanded) {
			NavigationBarItem(
				icon = { Icon(Icons.Default.Menu, contentDescription = "Меню") },
				label = { Text("Меню") },
				selected = false,
				onClick = onMenuClick
			)
		}
	}
}