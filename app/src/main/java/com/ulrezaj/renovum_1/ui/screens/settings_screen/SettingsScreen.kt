package com.ulrezaj.renovum_1.ui.screens.settings_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.ui.screens.settings_screen.components.AppSettingsTab
import com.ulrezaj.renovum_1.ui.screens.settings_screen.components.ProfileSettingsTab
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
	settings: UserSettings,
	onSettingsChange: (UserSettings) -> Unit
) {
	val tabs = listOf("Додаток", "Профіль")
	val pagerState = rememberPagerState(pageCount = { tabs.size })
	val coroutineScope = rememberCoroutineScope()

	Column(modifier = Modifier.fillMaxSize()) {
		PrimaryTabRow(
			selectedTabIndex = pagerState.currentPage,
			containerColor = MaterialTheme.colorScheme.surface,
			contentColor = MaterialTheme.colorScheme.primary
		) {
			tabs.forEachIndexed { index, title ->
				Tab(
					selected = pagerState.currentPage == index,
					onClick = {
						coroutineScope.launch {
							pagerState.animateScrollToPage(index)
						}
					},
					text = { Text(title) }
				)
			}
		}

		HorizontalPager(
			state = pagerState,
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
		) { page ->
			when (page) {
				0 -> AppSettingsTab(settings, onSettingsChange)
				1 -> ProfileSettingsTab(settings, onSettingsChange)
			}
		}
	}
}