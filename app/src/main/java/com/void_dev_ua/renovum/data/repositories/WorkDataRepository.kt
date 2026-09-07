package com.void_dev_ua.renovum.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.void_dev_ua.renovum.domain.model.WorkCategory
import com.void_dev_ua.renovum.domain.model.WorkSection
import com.void_dev_ua.renovum.domain.model.WorkService
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _allWorks = MutableStateFlow<List<WorkService>>(emptyList())
    val allWorksFlow: StateFlow<List<WorkService>> = _allWorks.asStateFlow()

    val allWorks: List<WorkService> get() = _allWorks.value

    val allSections = WorkSection.entries

    suspend fun loadWorks() {
        if (_allWorks.value.isNotEmpty()) return

        withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open("services.json")
                    .bufferedReader()
                    .use { it.readText() }

                val listType = object : TypeToken<List<WorkService>>() {}.type
                val works: List<WorkService>? = Gson().fromJson(jsonString, listType)

                if (works != null) {
                    _allWorks.value = works
                    L.d("WorkDataRepository: Успішно завантажено ${works.size} робіт з JSON!")
                } else {
                    L.e("WorkDataRepository: JSON завантажено, але список робіт порожній або некоректний")
                }
            } catch (e: Exception) {
                L.e("WorkDataRepository: Помилка завантаження JSON", e)
            }
        }
    }

    fun getCategoriesForSection(section: WorkSection): List<WorkCategory> {
        return WorkCategory.entries.filter { it.section == section }
    }

    fun getWorksForCategory(category: WorkCategory): List<WorkService> {
        return allWorks.filter { it.category == category }
    }
}
