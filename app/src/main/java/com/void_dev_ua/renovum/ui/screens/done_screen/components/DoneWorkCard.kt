package com.void_dev_ua.renovum.ui.screens.done_screen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.void_dev_ua.renovum.model.AppliedWork
import com.void_dev_ua.renovum.model.WorkService

@SuppressLint("DefaultLocale")
@Composable
fun DoneWorkCard(
	service: WorkService,
	applied: AppliedWork,
	onClick: () -> Unit
) {
	val totalSum = applied.priceAtTime * applied.quantity

	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 12.dp),
		onClick = onClick,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		border = BorderStroke(
			0.5.dp,
			MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
		)
	) {
		Row(
			modifier = Modifier
				.padding(12.dp)
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Column(modifier = Modifier.weight(1f)) {
				Text(
					text = service.name,
					style = MaterialTheme.typography.bodyLarge,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = "${applied.quantity} ${service.unit.displayName} × ${applied.priceAtTime.toInt()} ₴",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
				)
			}

			Text(
				text = "${String.format("%.2f", totalSum)} ₴",
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.primary
			)
		}
	}
}