package com.void_dev_ua.renovum.ui.screens.archive_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Допоміжний компонент для плавної появи чекбокса збоку
 */
@Composable
fun AnimatedCheckbox(
	visible: Boolean,
	checked: Boolean,
	onCheckedChange: () -> Unit
) {
	AnimatedVisibility(
		visible = visible,
		enter = slideInHorizontally(animationSpec = tween(200)) { -it / 2 },
		exit = slideOutHorizontally(animationSpec = tween(200)) { -it / 2 }
	) {
		Checkbox(
			checked = checked,
			onCheckedChange = { onCheckedChange() },
			modifier = Modifier.padding(end = 8.dp)
		)
	}
}