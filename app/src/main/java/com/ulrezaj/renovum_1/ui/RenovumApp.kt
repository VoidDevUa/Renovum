package com.ulrezaj.renovum_1.ui

import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.navigation.NavGraph
import com.ulrezaj.renovum_1.navigation.Screen
import com.ulrezaj.renovum_1.ui.components.BottomNav
import com.ulrezaj.renovum_1.ui.components.AppDrawer
import com.ulrezaj.renovum_1.ui.components.topAppBar.RenovumTopAppBar
import com.ulrezaj.renovum_1.ui.theme.Renovum_1Theme
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L
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
		Screen.allScreens.find { screen ->
			val baseRoute = screen.route.split("/")[0].split("?")[0]
			currentRoute?.startsWith(baseRoute) == true
		} ?: Screen.Rooms
	}

	val layoutDirection = if (userSettings.isLeftHanded) LayoutDirection.Ltr else LayoutDirection.Rtl

	LaunchedEffect(currentRoute) {
		L.nav("Current route changed to: $currentRoute")
	}

	Renovum_1Theme(appTheme = userSettings.appTheme) {
		CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

			if (drawerState.isOpen) {
				BackHandler(enabled = true) {
					L.d("Back pressed: closing drawer")
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
								L.click("Drawer Item: $route")
								navController.navigate(route) {
									popUpTo(navController.graph.findStartDestination().id) {
										saveState = true
									}
									launchSingleTop = true
									restoreState = true
								}
							},
							onCloseDrawer = {
								L.d("Closing drawer")
								scope.launch { drawerState.close() }
							}
						)
					}
				}
			) {
				CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
					Scaffold(
						topBar = {
							RenovumTopAppBar(
								currentScreenTitle = currentScreen.title,
								isLeftHanded = userSettings.isLeftHanded,
								isEditMode = isEditMode,

								selectedRoom = roomViewModel.selectedRoom,
								rooms = roomViewModel.rooms,

								onRoomSelected = if (currentRoute == Screen.Works.route) {
									{ room -> roomViewModel.selectRoom(room) }
								} else null,

								onEditClick = if (currentRoute == Screen.Rooms.route) {
									{ isEditMode = !isEditMode }
								} else null,

								onNavigateToEdit = if (currentRoute?.startsWith(Screen.Calculations.route) == true) {
									{
										val roomId = navBackStackEntry?.arguments?.getString("roomId")
										if (roomId != null) {
											navController.navigate(Screen.EditRoom.createRoute(roomId))
										}
									}
								} else null
							)
						},
						bottomBar = {
							BottomNav(
								isLeftHanded = userSettings.isLeftHanded,
								currentRoute = currentRoute,
								onNavigate = { route ->
									L.click("BottomNav Item: $route")
									navController.navigate(route) {
										popUpTo(navController.graph.findStartDestination().id) {
											saveState = true
										}
										launchSingleTop = true
										restoreState = true
									}
								},
								onMenuClick = {
									L.click("BottomNav: Open Drawer")
									scope.launch { drawerState.open() }
								}
							)
						}
					) { innerPadding ->
						NavGraph(
							navController = navController,
							paddingValues = innerPadding,
							userSettings = userSettings,
							onSettingsChange = { newSettings ->
								L.d("Settings updated: LeftHanded=${newSettings.isLeftHanded}")
								userSettings = newSettings
							},
							isEditMode = isEditMode,
							roomViewModel = roomViewModel,
							onDeleteRoom = { room ->
								L.click("Delete Room: ${room.name}")
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