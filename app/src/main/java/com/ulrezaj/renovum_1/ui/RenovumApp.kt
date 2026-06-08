package com.ulrezaj.renovum_1.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ulrezaj.renovum_1.data.UserSettingsManager
import com.ulrezaj.renovum_1.data.local.AppDatabase
import com.ulrezaj.renovum_1.data.repositories.RoomRepository
import com.ulrezaj.renovum_1.data.repositories.WorkRepository
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
	val context = LocalContext.current

	val settingsManager = remember { UserSettingsManager(context) }
	val userSettingsOrNull by settingsManager.userSettingsFlow.collectAsState(initial = null)

	val userSettings = userSettingsOrNull
	if (userSettings == null) {
		Box(modifier = Modifier.fillMaxSize())
		return
	}

	val roomViewModel: RoomViewModel = viewModel(
		factory = @Suppress("UNCHECKED_CAST") object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				val database = AppDatabase.getDatabase(context)

				val roomRepository = RoomRepository(database.roomDao())
				val workRepository = WorkRepository(database.appliedWorkDao())

				return RoomViewModel(roomRepository, workRepository) as T
			}
		}
	)

	val navController = rememberNavController()

	var isEditMode by remember { mutableStateOf(false) }

	val totalRawSum by roomViewModel.totalRawSumState.collectAsState()
	val currentDiscountedSum = totalRawSum * (1.0 - roomViewModel.projectDiscountPercent / 100.0)

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
				gesturesEnabled = false,
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
								totalSum = if (currentRoute == Screen.Done.route) currentDiscountedSum else null,
								onSumClick = if (currentRoute == Screen.Done.route) {
									{ roomViewModel.showDiscountDialog = true }
								} else null,
								selectedRoom = roomViewModel.selectedRoom.value,
								rooms = roomViewModel.rooms,
								onRoomSelected = if (currentRoute == Screen.Works.route || currentRoute == Screen.Ceiling.route) {
									{ room ->
										L.d("TopAppBar: Switching room inside ViewModel to ${room.name}")
										roomViewModel.selectRoom(room)
									}
								} else null,
								onEditClick = when (currentRoute) {
									Screen.Rooms.route -> {
										{ isEditMode = !isEditMode }
									}
									Screen.Archive.route -> {
										{ roomViewModel.isArchiveSelectMode = !roomViewModel.isArchiveSelectMode }
									}
									else -> null
								},
								onNavigateToEdit = if (currentRoute?.startsWith(Screen.Calculations.route) == true) {
									{
										val roomId = roomViewModel.selectedRoom.value?.id
										if (roomId != null) {
											L.nav("Navigating to EditRoom for id: $roomId")
											navController.navigate(Screen.EditRoom.route)
										} else {
											L.e("Navigation Error: selectedRoom is null")
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
								scope.launch {
									settingsManager.saveSettings(newSettings)
								}
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