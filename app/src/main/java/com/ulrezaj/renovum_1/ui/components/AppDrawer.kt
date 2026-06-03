package com.ulrezaj.renovum_1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.navigation.Screen
import com.ulrezaj.renovum_1.ui.components.list_Items.DrawerItem
import com.ulrezaj.renovum_1.utility.L

@Composable
fun AppDrawer(
	currentRoute: String?,
	onNavigate: (String) -> Unit,
	onCloseDrawer: () -> Unit
) {
	ModalDrawerSheet (
		drawerContainerColor = MaterialTheme.colorScheme.surface,
		drawerTonalElevation = 0.dp
	){
		Column(
			modifier = Modifier
				.fillMaxHeight()
				.clickable(
					interactionSource = remember { MutableInteractionSource() },
					indication = null
				) {
					L.d("AppDrawer: Background clicked - closing")
					onCloseDrawer()
				}
		) {
			Text(
				"Renovum",
				modifier = Modifier.padding(16.dp),
				style = MaterialTheme.typography.titleLarge
			)
			HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

			DrawerItem(
				screen = Screen.Settings,
				selected = currentRoute == Screen.Settings.route,
				onNavigate = { route ->
					L.click("Drawer: Settings selected")
					onNavigate(route)
				},
				onCloseDrawer = onCloseDrawer
			)
			DrawerItem(
				screen = Screen.Ceiling,
				selected = currentRoute == Screen.Ceiling.route,
				onNavigate = { route ->
					L.click("Drawer: Ceiling selected")
					onNavigate(route)
				},
				onCloseDrawer = onCloseDrawer
			)
			DrawerItem(
				screen = Screen.Archive,
				selected = currentRoute == Screen.Archive.route,
				onNavigate = { route ->
					L.click("Drawer: Archive selected")
					onNavigate(route)
				},
				onCloseDrawer = onCloseDrawer
			)
			/*
			DrawerItem(
				screen = Screen.Materials,
				selected = currentRoute == Screen.Materials.route,
				onNavigate = { route ->
					L.click("Drawer: Materials selected")
					onNavigate(route)
				},
				onCloseDrawer = onCloseDrawer
			)
			*/
			DrawerItem(
				screen = Screen.About,
				selected = currentRoute == Screen.About.route,
				onNavigate = { route ->
					L.click("Drawer: About selected")
					onNavigate(route)
				},
				onCloseDrawer = onCloseDrawer
			)
		}
	}
}