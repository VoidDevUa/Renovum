package com.ulrezaj.renovum_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ulrezaj.renovum_1.ui.RenovumApp
import com.ulrezaj.renovum_1.utility.L

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		L.d("MainActivity onCreate: App started")

		enableEdgeToEdge()

		setContent {
			L.d("MainActivity setContent: Initializing RenovumApp")
			RenovumApp()
		}
	}
}