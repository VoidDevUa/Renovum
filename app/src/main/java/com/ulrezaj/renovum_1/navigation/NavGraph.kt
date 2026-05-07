package com.ulrezaj.renovum_1.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.data.model.RoomShapeType
import com.ulrezaj.renovum_1.ui.screens.AboutScreen
import com.ulrezaj.renovum_1.ui.screens.AddRoomScreen
import com.ulrezaj.renovum_1.ui.screens.CalcScreen
import com.ulrezaj.renovum_1.ui.screens.CeilingScreen
import com.ulrezaj.renovum_1.ui.screens.DoneScreen
import com.ulrezaj.renovum_1.ui.screens.JobsScreen
import com.ulrezaj.renovum_1.ui.screens.MaterialsScreen
import com.ulrezaj.renovum_1.ui.screens.RoomsScreen
import com.ulrezaj.renovum_1.ui.screens.SettingsScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ulrezaj.renovum_1.ui.viewmodels.RoomViewModel

@Composable
fun NavGraph(
	navController: NavHostController,
	paddingValues: PaddingValues,
	userSettings: UserSettings,
	onSettingsChange: (UserSettings) -> Unit,
	isEditMode: Boolean,
	onDeleteRoom: (RoomEntity) -> Unit
) {
	val roomViewModel: RoomViewModel = viewModel()

	NavHost(
		navController = navController,
		startDestination = Screen.Rooms.route,
		modifier = Modifier.padding(paddingValues)
	) {
		composable(route = Screen.Rooms.route) {
			RoomsScreen(
				rooms = roomViewModel.rooms,
				userSettings = userSettings,
				isEditMode = isEditMode,
				onAddRoomClick = {
					navController.navigate(Screen.AddRoom.route)
				},
				onRoomClick = { room ->
					navController.navigate(Screen.Calculations.route)
				},
				onDeleteRoom = onDeleteRoom
			)
		}
		composable(Screen.Calculations.route) { CalcScreen() }
		composable(Screen.Jobs.route) { JobsScreen() }
		composable(Screen.Done.route) { DoneScreen() }
		composable(Screen.Settings.route) {
			SettingsScreen(
				settings = userSettings,
				onSettingsChange = onSettingsChange
			)
		}
		composable(Screen.Materials.route) { MaterialsScreen() }
		composable(Screen.Ceiling.route) { CeilingScreen() }
		composable(Screen.About.route) { AboutScreen() }
		composable(Screen.AddRoom.route) {
			AddRoomScreen(
				initialShapeType = RoomShapeType.RECTANGLE,
				navController = navController,
				userSettings = userSettings,
				roomViewModel = roomViewModel,
				onSave = { navController.popBackStack() }
			)
		}
	}
}