package com.ulrezaj.renovum_1.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ulrezaj.renovum_1.data.model.WorkCategory
import com.ulrezaj.renovum_1.data.model.WorkSection
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.utility.L

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
			L.d("WorkDataRepository: Помилка завантаження JSON: ${e.message}")
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