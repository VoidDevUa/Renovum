package com.void_dev_ua.renovum.ui.screens.about_screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun AboutScreen() {
	val context = LocalContext.current
	val email = "void.dev.ua@gmail.com"

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
			.verticalScroll(rememberScrollState()),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Text(
			text = "Renovum",
			style = MaterialTheme.typography.headlineLarge,
			color = MaterialTheme.colorScheme.primary,
			fontWeight = FontWeight.Bold
		)

		Text(text = "Версія 1.0.0", style = MaterialTheme.typography.bodyMedium)

		HorizontalDivider(
			modifier = Modifier.padding(vertical = 8.dp),
			thickness = DividerDefaults.Thickness,
			color = DividerDefaults.color
		)

		Text(
			text = "Renovum — це професійний інструмент для розрахунку кошторисів ремонтних робіт. " +
					"Додаток дозволяє вести облік робіт за кімнатами, розраховувати їх параметри, " +
					"фіксувати виконані роботи кожної кімнати за їх цінами та об'ємом, " +
					"а також генерувати готові звіти у форматі Word.",
			style = MaterialTheme.typography.bodyMedium,
			textAlign = TextAlign.Center
		)

		Spacer(modifier = Modifier.height(8.dp))

		Card(
			modifier = Modifier.fillMaxWidth(),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
		) {
			Column(modifier = Modifier.padding(12.dp)) {
				Text("Розробник:", fontWeight = FontWeight.Bold)
				Text("Огінський Н. А.")
				Text("Студент СП-42, ТНТУ ім. Івана Пулюя")
				Spacer(modifier = Modifier.height(8.dp))
				Text("Кваліфікаційна робота бакалавра", fontWeight = FontWeight.Bold)
				Text("2026 рік")
			}
		}

		Button(
			onClick = {
				val intent = Intent(Intent.ACTION_SENDTO).apply {
					data = "mailto:$email".toUri()
					putExtra(Intent.EXTRA_SUBJECT, "Відгук про Renovum")
				}
				try {
					context.startActivity(intent)
				} catch (_: Exception) {
					Toast.makeText(
						context,
						"Не знайдено поштовий клієнт для відправки листа",
						Toast.LENGTH_SHORT
					).show()
				}
			},
			modifier = Modifier.fillMaxWidth()
		) {
			Icon(Icons.Default.Email, contentDescription = "Картинка емейл")
			Spacer(modifier = Modifier.width(8.dp))
			Text("Написати розробнику")
		}

		Text(
			text = "Відмова від відповідальності",
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.secondary,
			modifier = Modifier.padding(top = 8.dp)
		)
		Text(
			text = "Згенеровані звіти мають виключно ознайомчий характер і не є офіційним фінансовим чи юридичним документом.",
			style = MaterialTheme.typography.bodySmall,
			textAlign = TextAlign.Center,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)
	}
}