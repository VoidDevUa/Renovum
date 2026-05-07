package com.ulrezaj.renovum_1.utility

import android.util.Log

object L {
	private const val TAG = "RENOVUM_DEBUG"

	fun d(message: String) {
		Log.d(TAG, "+++  INFO: $message")
	}
	fun e(message: String, throwable: Throwable? = null) {
		Log.e(TAG, "!!!  ERROR: $message", throwable)
	}
	fun nav(route: String?) {
		Log.i(TAG, "---  NAVIGATING TO: $route")
	}
	fun click(label: String) {
		Log.d(TAG, "===  CLICK: $label")
	}
}