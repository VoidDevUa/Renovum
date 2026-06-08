package com.ulrezaj.renovum_1.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.data.model.RoomShapeType
import com.ulrezaj.renovum_1.ui.screens.AboutScreen
import com.ulrezaj.renovum_1.ui.screens.secondary_screens.AddRoomScreen
import com.ulrezaj.renovum_1.ui.screens.calc_screen.CalcScreen
import com.ulrezaj.renovum_1.ui.screens.CeilingScreen
import com.ulrezaj.renovum_1.ui.screens.DoneScreen
import com.ulrezaj.renovum_1.ui.screens.secondary_screens.EditRoomScreen
import com.ulrezaj.renovum_1.ui.screens.MaterialsScreen
import com.ulrezaj.renovum_1.ui.screens.rooms_screen.RoomsScreen
import com.ulrezaj.renovum_1.ui.screens.settings_screen.SettingsScreen
import com.ulrezaj.renovum_1.ui.screens.WorksScreen
import com.ulrezaj.renovum_1.ui.screens.archive_screen.ArchiveScreen
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel
import com.ulrezaj.renovum_1.utility.L

@Composable
fun NavGraph(
	navController: NavHostController,
	paddingValues: PaddingValues,
	userSettings: UserSettings,
	onSettingsChange: (UserSettings) -> Unit,
	isEditMode: Boolean,
	roomViewModel: RoomViewModel,
	onDeleteRoom: (RoomEntity) -> Unit
) {
	NavHost(
		navController = navController,
		startDestination = Screen.Rooms.route,
		modifier = Modifier.padding(paddingValues)
	) {
		composable(Screen.Rooms.route) {
			LaunchedEffect(Unit) {
				L.nav("Screen: Rooms")
			}
			RoomsScreen(
				rooms = roomViewModel.rooms,
				userSettings = userSettings,
				isEditMode = isEditMode,
				onAddRoomClick = {
					L.click("Add Room button")
					navController.navigate(Screen.AddRoom.route)
				},
				onRoomClick = { room ->
					L.nav("Rooms -> Selecting Room and Navigating to Calc: ${room.id}")
					roomViewModel.selectRoom(room)

					navController.navigate(Screen.Calculations.route) {
						popUpTo(Screen.Rooms.route) { saveState = true }
						launchSingleTop = true
						restoreState = true
					}
				},
				onDeleteRoom = onDeleteRoom,
				onSettingsChange = onSettingsChange
			)
		}
		composable(Screen.Calculations.route) {
			L.d("NavGraph: Rendering CalcScreen")

			val rooms = roomViewModel.rooms
			val activeRoom by roomViewModel.selectedRoom

			LaunchedEffect(activeRoom?.id, rooms) {
				if (activeRoom == null && rooms.isNotEmpty()) {
					roomViewModel.selectRoom(rooms.first())
				}
			}

			val room = activeRoom
			if (room != null) {
				CalcScreen(
					currentRoom = room,
					roomViewModel = roomViewModel,
					allRooms = rooms,
					userSettings = userSettings,
					onRoomSelected = { selected ->
						L.nav("Switching Calc room inside ViewModel to: ${selected.id}")
						roomViewModel.selectRoom(selected)
					}
				)
			} else {
				L.e("NavGraph: No rooms available to show in CalcScreen")
				Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text("Спершу додайте кімнату")
				}
			}
		}
		composable(Screen.Works.route) {
			L.nav("Screen: Works")
			val rooms = roomViewModel.rooms
			val activeRoom by roomViewModel.selectedRoom

			LaunchedEffect(activeRoom?.id, rooms) {
				if (activeRoom == null && rooms.isNotEmpty()) {
					roomViewModel.selectRoom(rooms.first())
				}
			}

			WorksScreen(
				roomViewModel = roomViewModel,
				userSettings = userSettings
			)
		}
		composable(Screen.Done.route) {
			LaunchedEffect(Unit) { L.nav("Screen: Done") }
			DoneScreen(
				roomViewModel = roomViewModel,
				userSettings = userSettings
			)
		}
		composable(Screen.Settings.route) {
			LaunchedEffect(Unit) { L.nav("Screen: Settings") }
			SettingsScreen(
				settings = userSettings,
				onSettingsChange = onSettingsChange
			)
		}
		composable(Screen.Ceiling.route) {
			LaunchedEffect(Unit) { L.nav("Screen: Ceiling") }
			CeilingScreen()
		}
		composable(Screen.Materials.route) {
			LaunchedEffect(Unit) { L.nav("Screen: Materials") }
			MaterialsScreen()
		}
		composable(Screen.Archive.route) {
			LaunchedEffect(Unit) { L.nav("Screen: Archive") }
			ArchiveScreen(
				roomViewModel = roomViewModel,
				userSettings = userSettings
			)
		}
		composable(Screen.About.route) {
			LaunchedEffect(Unit) { L.nav("Screen: About") }
			AboutScreen()
		}
		composable(Screen.AddRoom.route) {
			LaunchedEffect(Unit) { L.nav("Screen: AddRoom") }
			AddRoomScreen(
				initialShapeType = RoomShapeType.RECTANGLE,
				navController = navController,
				userSettings = userSettings,
				roomViewModel = roomViewModel,
				onSave = {
					L.d("AddRoom: Save success, popping backstack")
					navController.popBackStack()
				}
			)
		}
		composable(route = Screen.EditRoom.route) {
			EditRoomScreen(
				navController = navController,
				roomViewModel = roomViewModel,
				onSave = {
					navController.popBackStack()
				}
			)
		}
	}
}