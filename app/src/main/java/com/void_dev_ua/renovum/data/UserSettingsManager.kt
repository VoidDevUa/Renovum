package com.void_dev_ua.renovum.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_settings")

class UserSettingsManager(private val context: Context) {

	private companion object {
		val APP_THEME_KEY = stringPreferencesKey("app_theme")
		val SHOW_DIMENSIONS_KEY = booleanPreferencesKey("show_dimensions")
		val IS_LEFT_HANDED_KEY = booleanPreferencesKey("is_left_handed")
		val DIALOG_COLUMNS_KEY = intPreferencesKey("dialog_columns")

		val MASTER_NAME_KEY = stringPreferencesKey("master_name")
		val MASTER_PHONE_KEY = stringPreferencesKey("master_phone")
		val CURRENT_OBJECT_ADDRESS_KEY = stringPreferencesKey("current_object_address")
	}

	val userSettingsFlow: Flow<UserSettings> = context.dataStore.data.map { preferences ->
		val themeString = preferences[APP_THEME_KEY] ?: "SYSTEM"
		val theme = try {
			com.void_dev_ua.renovum.ui.theme.AppTheme.valueOf(themeString)
		} catch (_: Exception) {
			com.void_dev_ua.renovum.ui.theme.AppTheme.SYSTEM
		}

		UserSettings(
			appTheme = theme,
			showDimensionsInCard = preferences[SHOW_DIMENSIONS_KEY] ?: true,
			isLeftHanded = preferences[IS_LEFT_HANDED_KEY] ?: false,
			dialogColumns = preferences[DIALOG_COLUMNS_KEY] ?: 2,
			masterName = preferences[MASTER_NAME_KEY] ?: "",
			masterPhone = preferences[MASTER_PHONE_KEY] ?: "",
			currentObjectAddress = preferences[CURRENT_OBJECT_ADDRESS_KEY] ?: ""
		)
	}

	suspend fun saveSettings(settings: UserSettings) {
		context.dataStore.edit { preferences ->
			preferences[APP_THEME_KEY] = settings.appTheme.name
			preferences[SHOW_DIMENSIONS_KEY] = settings.showDimensionsInCard
			preferences[IS_LEFT_HANDED_KEY] = settings.isLeftHanded
			preferences[DIALOG_COLUMNS_KEY] = settings.dialogColumns
			preferences[MASTER_NAME_KEY] = settings.masterName
			preferences[MASTER_PHONE_KEY] = settings.masterPhone
			preferences[CURRENT_OBJECT_ADDRESS_KEY] = settings.currentObjectAddress
		}
	}
}