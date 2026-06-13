package com.void_dev_ua.renovum.navigation

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
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.model.RoomEntity
import com.void_dev_ua.renovum.model.RoomShapeType
import com.void_dev_ua.renovum.ui.screens.about_screen.AboutScreen
import com.void_dev_ua.renovum.ui.screens.secondary_screens.AddRoomScreen
import com.void_dev_ua.renovum.ui.screens.calc_screen.CalcScreen
import com.void_dev_ua.renovum.ui.screens.screens_to_add.CeilingScreen
import com.void_dev_ua.renovum.ui.screens.done_screen.DoneScreen
import com.void_dev_ua.renovum.ui.screens.secondary_screens.EditRoomScreen
import com.void_dev_ua.renovum.ui.screens.screens_to_add.MaterialsScreen
import com.void_dev_ua.renovum.ui.screens.rooms_screen.RoomsScreen
import com.void_dev_ua.renovum.ui.screens.settings_screen.SettingsScreen
import com.void_dev_ua.renovum.ui.screens.works_screen.WorksScreen
import com.void_dev_ua.renovum.ui.screens.archive_screen.ArchiveScreen
import com.void_dev_ua.renovum.viewmodel.RoomViewModel
import com.void_dev_ua.renovum.utility.L

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
			LaunchedEffect(Unit) { L.nav("Screen: Rooms") }
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
			L.nav("Screen: Calc")

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
		composable(Screen.EditRoom.route) {
			LaunchedEffect(Unit) { L.nav("Screen: EditRoom") }
			EditRoomScreen(
				navController = navController,
				roomViewModel = roomViewModel,
				onSave = {
					L.d("EditRoom: Save success, popping backstack")
					navController.popBackStack()
				}
			)
		}
	}
}