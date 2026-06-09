package com.void_dev_ua.renovum.ui.components.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.void_dev_ua.renovum.data.model.RoomShapeType
import com.void_dev_ua.renovum.utility.L

@Composable
fun RoomShapeDialog(
	onDismiss: () -> Unit,
	onNext: (RoomShapeType) -> Unit,
	columns: Int
) {
	var selectedShape by remember { mutableStateOf<RoomShapeType?>(null) }

	val shapes = RoomShapeType.entries

	Dialog(onDismissRequest = {
		L.d("RoomShapeDialog: Dismissed via outside click")
		onDismiss()
	}) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight(),
			shape = RoundedCornerShape(24.dp),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
		) {
			Column(
				modifier = Modifier.padding(20.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = "Оберіть форму кімнати:",
					style = MaterialTheme.typography.titleLarge,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.padding(bottom = 16.dp)
				)

				LazyVerticalGrid(
					columns = GridCells.Fixed(columns),
					modifier = Modifier
						.fillMaxWidth()
						.height(300.dp),
					contentPadding = PaddingValues(4.dp),
					verticalArrangement = Arrangement.spacedBy(12.dp),
					horizontalArrangement = Arrangement.spacedBy(12.dp)
				) {
					items(shapes) { shape ->
						val isSelected = selectedShape == shape
						ShapeGridItem(
							label = shape.getDisplayName(),
							isSelected = isSelected,
							columns = columns,
							onClick = {
								L.click("ShapeDialog: Selected ${shape.name}")
								selectedShape = shape
							}
						)
					}
				}

				Spacer(modifier = Modifier.height(24.dp))

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					TextButton(onClick = {
						L.click("ShapeDialog: Cancel clicked")
						onDismiss()
					}) {
						Text("Скасувати", color = MaterialTheme.colorScheme.error)
					}

					Button(
						onClick = {
							selectedShape?.let {
								L.click("ShapeDialog: Proceed with $it")
								onNext(it)
							}
						},
						enabled = selectedShape != null,
						colors = ButtonDefaults.buttonColors(
							containerColor = Color(0xFF2E7D32)
						)
					) {
						Text("Далі")
					}
				}
			}
		}
	}
}

@Composable
fun ShapeGridItem(
	label: String,
	isSelected: Boolean,
	columns: Int,
	onClick: () -> Unit
) {
	val borderColor = if (isSelected) Color(0xFF2E7D32) else Color.LightGray.copy(alpha = 0.5f)
	val borderWidth = if (isSelected) 3.dp else 1.dp

	Column(
		modifier = Modifier
			.clip(RoundedCornerShape(12.dp))
			.clickable { onClick() }
			.border(BorderStroke(borderWidth, borderColor), RoundedCornerShape(12.dp))
			.background(if (isSelected) Color(0xFF2E7D32).copy(alpha = 0.05f) else Color.Transparent)
			.padding(8.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Box(
			modifier = Modifier
				.size(80.dp)
				.background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
			contentAlignment = Alignment.Center
		) {
			if (isSelected) {
				Icon(
					imageVector = Icons.Default.CheckCircle,
					contentDescription = null,
					tint = Color(0xFF2E7D32),
					modifier = Modifier
						.align(Alignment.TopEnd)
						.padding(4.dp)
						.size(20.dp)
				)
			}
			Text("IMG", color = Color.Gray)
		}

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = label,
			style = MaterialTheme.typography.bodySmall,
			fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
			color = if (isSelected) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface,
			textAlign = androidx.compose.ui.text.style.TextAlign.Center,
			minLines = if(columns == 3) 2 else 1,
			modifier = Modifier.fillMaxWidth()
		)
	}
}