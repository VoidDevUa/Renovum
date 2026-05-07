package com.ulrezaj.renovum_1.ui.components.list_Items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ulrezaj.renovum_1.data.model.WorkService

@Composable
fun WorkCard(
	work: WorkService,
	isLeftHanded: Boolean,
	enabled: Boolean,
	isDone: Boolean,
	onAddClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.height(72.dp)
			.alpha(if (enabled || isDone) 1f else 0.5f),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		border = BorderStroke(
			1.dp,
			MaterialTheme.colorScheme.onSurface.copy(alpha = if (isDone) 0.05f else 0.12f)
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = 12.dp, vertical = 8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			if (isLeftHanded) {
				StatusActionButton(
					isDone = isDone,
					enabled = enabled,
					onClick = onAddClick
				)
			}

			Column(
				modifier = Modifier.weight(1f),
				verticalArrangement = Arrangement.Center
			) {
				Text(
					text = work.name,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (isDone) 0.6f else 1f),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = "від 100 до 500 грн / ${work.unit.displayName}",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
				)
			}

			Column(
				horizontalAlignment = Alignment.End
			) {
				Text(
					text = "${work.averagePrice.toInt()} ₴",
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.primary.copy(alpha = if (isDone) 0.6f else 1f)
				)
				Text(
					text = "середня",
					style = MaterialTheme.typography.labelMedium,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
				)
			}

			if (!isLeftHanded) {
				StatusActionButton(
					isDone = isDone,
					enabled = enabled,
					onClick = onAddClick
				)
			}
		}
	}
}

@Composable
fun StatusActionButton(
	isDone: Boolean,
	enabled: Boolean,
	onClick: () -> Unit
) {
	if (isDone) {
		Icon(
			imageVector = Icons.Default.Check,
			contentDescription = "Додано",
			tint = MaterialTheme.colorScheme.primary,
			modifier = Modifier
				.padding(8.dp)
				.size(24.dp)
		)
	} else {
		IconButton(
			onClick = onClick,
			enabled = enabled,
			modifier = Modifier
				.size(40.dp)
				.background(
					color = MaterialTheme.colorScheme.primary.copy(
						alpha = if (enabled) 0.1f else 0.05f
					),
					shape = CircleShape
				)
		) {
			Icon(
				imageVector = Icons.Default.Add,
				contentDescription = "Додати",
				tint = MaterialTheme.colorScheme.primary.copy(
					alpha = if (enabled) 1f else 0.3f
				)
			)
		}
	}
}