package com.ulrezaj.renovum_1.ui

import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.navigation.NavGraph
import com.ulrezaj.renovum_1.navigation.Screen
import com.ulrezaj.renovum_1.ui.components.BottomNav
import com.ulrezaj.renovum_1.ui.components.AppDrawer
import com.ulrezaj.renovum_1.ui.components.RenovumTopAppBar
import com.ulrezaj.renovum_1.ui.theme.Renovum_1Theme
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovumApp() {
	val roomViewModel: RoomViewModel = viewModel()
	val navController = rememberNavController()

	var userSettings by remember { mutableStateOf(UserSettings()) }

	var isEditMode by remember { mutableStateOf(false) }

	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	val scope = rememberCoroutineScope()

	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry?.destination?.route

	val currentScreen = remember(currentRoute) {
		Screen.allScreens.find { it.route == currentRoute } ?: Screen.Rooms
	}

	val layoutDirection = if (userSettings.isLeftHanded) LayoutDirection.Ltr else LayoutDirection.Rtl

	Renovum_1Theme(appTheme = userSettings.appTheme) {
		CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

			if (drawerState.isOpen) {
				BackHandler(enabled = true) {
					scope.launch { drawerState.close() }
				}
			}

			ModalNavigationDrawer(
				drawerState = drawerState,
				gesturesEnabled = true,
				drawerContent = {
					CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
						AppDrawer(
							currentRoute = currentRoute,
							onNavigate = { route ->
								navController.navigate(route) {
									launchSingleTop = true
									restoreState = true
								}
							},
							onCloseDrawer = { scope.launch { drawerState.close() } }
						)
					}
				}
			) {
				CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
					Scaffold(
						topBar = {
							RenovumTopAppBar(
								currentScreenTitle = currentScreen.title,
								isEditMode = isEditMode,
								onEditClick = if (currentRoute == Screen.Rooms.route) {
									{ isEditMode = !isEditMode }
								} else null
							)
						},
						bottomBar = {
							BottomNav(
								isLeftHanded = userSettings.isLeftHanded,
								currentRoute = currentRoute,
								onNavigate = { route ->
									navController.navigate(route) {
										launchSingleTop = true
										restoreState = true
									}
								},
								onMenuClick = { scope.launch { drawerState.open() } }
							)
						}
					) { innerPadding ->
						NavGraph(
							navController = navController,
							paddingValues = innerPadding,
							userSettings = userSettings,
							onSettingsChange = { newSettings ->
								userSettings = newSettings
							},
							isEditMode = isEditMode,
							onDeleteRoom = { room ->
								roomViewModel.deleteRoom(room)
								isEditMode = false
							}
						)
					}
				}
			}
		}
	}
}