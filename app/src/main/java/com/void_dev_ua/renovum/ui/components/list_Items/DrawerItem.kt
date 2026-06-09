package com.void_dev_ua.renovum.ui.components.list_Items

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.void_dev_ua.renovum.navigation.Screen

@Composable
fun DrawerItem(
	screen: Screen,
	selected: Boolean,
	onNavigate: (String) -> Unit,
	onCloseDrawer: () -> Unit
) {
	NavigationDrawerItem(
		label = { Text(screen.title) },
		icon = { Icon(screen.icon, contentDescription = null) },
		selected = selected,
		onClick = {
			onNavigate(screen.route)
			onCloseDrawer()
		},
		modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
		colors = NavigationDrawerItemDefaults.colors(
			selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
			selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
			selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
			unselectedContainerColor = Color.Transparent,
			unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
			unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
		)
	)
}