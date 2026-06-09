package com.void_dev_ua.renovum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.void_dev_ua.renovum.data.repositories.WorkDataRepository
import com.void_dev_ua.renovum.ui.RenovumApp
import com.void_dev_ua.renovum.utility.L

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		L.d("MainActivity onCreate: App started")

		enableEdgeToEdge()

		WorkDataRepository.init(applicationContext)

		setContent {
			L.d("MainActivity setContent: Initializing RenovumApp")
			RenovumApp()
		}
	}
}