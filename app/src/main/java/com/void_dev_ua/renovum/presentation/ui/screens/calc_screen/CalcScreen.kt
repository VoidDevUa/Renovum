package com.void_dev_ua.renovum.presentation.ui.screens.calc_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.data.UserSettings
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.presentation.ui.components.RoomSchemaPainter
import com.void_dev_ua.renovum.presentation.ui.components.topAppBar.elements.RoomSelectorDropdown
import com.void_dev_ua.renovum.presentation.ui.screens.calc_screen.components.RoomOpeningsPage
import com.void_dev_ua.renovum.presentation.ui.screens.calc_screen.components.RoomResultsPage
import com.void_dev_ua.renovum.presentation.ui.screens.calc_screen.components.CalcPagerHeader
import com.void_dev_ua.renovum.presentation.viewmodel.CalcScreenViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CalcScreen(
	currentRoom: RoomEntity,
	roomViewModel: CalcScreenViewModel,
	userSettings: UserSettings
) {
	val allRooms by roomViewModel.rooms.collectAsState()
	val calcData by roomViewModel.calculationData.collectAsState()
	
	val pagerState = rememberPagerState(pageCount = { 2 })
	val scope = rememberCoroutineScope()

	Column(modifier = Modifier
		.fillMaxSize()
		.padding(horizontal = 16.dp, vertical = 8.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			val selector = @Composable {
				RoomSelectorDropdown(
					selectedRoom = currentRoom,
					rooms = allRooms,
					onRoomSelected = { roomViewModel.selectRoom(it) },
					isOutlined = true
				)
			}
			val title = @Composable {
				Text(text = "Форма: ${currentRoom.shapeType.getDisplayName()}", style = MaterialTheme.typography.bodyMedium)
			}

			if (userSettings.isLeftHanded) {
				selector()
				title()
			} else {
				title()
				selector()
			}
		}

		RoomSchemaPainter(
			shapeType = currentRoom.shapeType,
			focusedField = null,
			paramValues = currentRoom.params.toMap(),
			modifier = Modifier.height(200.dp).fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(8.dp))

		CalcPagerHeader(pagerState = pagerState, scope = scope)

		Spacer(modifier = Modifier.height(12.dp))

		HorizontalPager(
			state = pagerState,
			modifier = Modifier.weight(1f)
		) { page ->
			Column(
				modifier = Modifier
					.fillMaxSize()
					.verticalScroll(rememberScrollState()),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				when (page) {
					0 -> calcData?.let { RoomResultsPage(currentRoom = currentRoom, data = it) }
					1 -> RoomOpeningsPage(currentRoom = currentRoom)
				}
			}
		}
	}
}