package com.void_dev_ua.renovum.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.void_dev_ua.renovum.model.WorkCategory
import com.void_dev_ua.renovum.model.WorkSection
import com.void_dev_ua.renovum.model.WorkService
import com.void_dev_ua.renovum.utility.L

object WorkDataRepository {

	val allSections = WorkSection.entries

	fun getCategoriesForSection(section: WorkSection): List<WorkCategory> {
		return WorkCategory.entries.filter { it.section == section }
	}

	var allWorks: List<WorkService> = emptyList()
		private set

	fun init(context: Context) {
		if (allWorks.isNotEmpty()) return

		try {
			val jsonString = context.assets.open("services.json")
				.bufferedReader()
				.use { it.readText() }

			val listType = object : TypeToken<List<WorkService>>() {}.type

			allWorks = Gson().fromJson(jsonString, listType)

			L.d("WorkDataRepository: Успішно завантажено ${allWorks.size} робіт з JSON!")
		} catch (e: Exception) {
			L.e("WorkDataRepository: Помилка завантаження JSON: ${e.message}")
			allWorks = emptyList()
		}
	}

	/**
	 * Отримати роботи для конкретної категорії
	*/
	fun getWorksForCategory(category: WorkCategory): List<WorkService> {
		return allWorks.filter { it.category == category }
	}
}