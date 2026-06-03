package com.ulrezaj.renovum_1.ui.screens.calc_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CalcPagerHeader(
	pagerState: PagerState,
	scope: CoroutineScope
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.height(44.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
		)
	) {
		Row(
			modifier = Modifier.fillMaxSize(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			IconButton(
				onClick = {
					if (pagerState.currentPage > 0) {
						scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
					}
				},
				enabled = pagerState.currentPage > 0
			) {
				Icon(
					imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
					contentDescription = "Назад",
					tint = if (pagerState.currentPage > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
				)
			}

			val headerText = when (pagerState.currentPage) {
				0 -> "Розрахунки та розміри"
				else -> "Вікна та двері (прорізи)"
			}

			Text(
				text = headerText,
				style = MaterialTheme.typography.titleSmall,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.weight(1f),
				textAlign = TextAlign.Center
			)

			IconButton(
				onClick = {
					if (pagerState.currentPage < 1) {
						scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
					}
				},
				enabled = pagerState.currentPage < 1
			) {
				Icon(
					imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
					contentDescription = "Вперед",
					tint = if (pagerState.currentPage < 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
				)
			}
		}
	}
}