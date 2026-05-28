package com.ulrezaj.renovum_1.data.repositories

import com.ulrezaj.renovum_1.data.model.TargetSurface
import com.ulrezaj.renovum_1.data.model.WorkCategory
import com.ulrezaj.renovum_1.data.model.WorkSection
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.data.model.WorkUnit

object WorkDataRepository {

	val allSections = WorkSection.entries

	fun getCategoriesForSection(section: WorkSection): List<WorkCategory> {
		return WorkCategory.entries.filter { it.section == section }
	}

	val allWorks = listOf   (
		//region ОЗДОБЛЮВАЛЬНІ РОБОТИ
		//region Плиточні роботи
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання плитки на підлогу",
			unit = WorkUnit.M2,
			minPrice = 450.0,
			maxPrice = 800.0,
			averagePrice = 600.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання плитки на стіни",
			unit = WorkUnit.M2,
			minPrice = 450.0,
			maxPrice = 900.0,
			averagePrice = 630.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Грунтовка стяжки під плитку",
			unit = WorkUnit.M2,
			minPrice = 20.0,
			maxPrice = 40.0,
			averagePrice = 29.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Різання плитки, шліфування кромок",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 250.0,
			averagePrice = 200.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Установка куточків при укладанні плитки",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 200.0,
			averagePrice = 144.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Затирка швів плитки (стандартна)",
			unit = WorkUnit.M2,
			minPrice = 75.0,
			maxPrice = 200.0,
			averagePrice = 121.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Отвір в плитці під розетку або трубу",
			unit = WorkUnit.PCS,
			minPrice = 120.0,
			maxPrice = 250.0,
			averagePrice = 188.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Розпилювання та шліфування країв плитки под 45 градусів",
			unit = WorkUnit.MPOG,
			minPrice = 300.0,
			maxPrice = 600.0,
			averagePrice = 467.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання плитки кабанчик",
			unit = WorkUnit.M2,
			minPrice = 600.0,
			maxPrice = 1500.0,
			averagePrice = 1024.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Декоративна гіпсова плитка",
			unit = WorkUnit.M2,
			minPrice = 500.0,
			maxPrice = 1200.0,
			averagePrice = 879.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Поклейка гіпсової цеглинки на стіни",
			unit = WorkUnit.M2,
			minPrice = 500.0,
			maxPrice = 1200.0,
			averagePrice = 870.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Монтаж керамічних плінтусів",
			unit = WorkUnit.MPOG,
			minPrice = 300.0,
			maxPrice = 500.0,
			averagePrice = 367.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Виготовлення та монтаж ревізійного люка з плитки на магнітах",
			unit = WorkUnit.PCS,
			minPrice = 900.0,
			maxPrice = 2500.0,
			averagePrice = 1527.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Монтаж плиткового декоративного куточка",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 290.0,
			averagePrice = 199.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Установка керамічного фриза до 100 мм",
			unit = WorkUnit.MPOG,
			minPrice = 200.0,
			maxPrice = 600.0,
			averagePrice = 339.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Облицювання стін мозаїкою",
			unit = WorkUnit.M2,
			minPrice = 700.0,
			maxPrice = 2000.0,
			averagePrice = 1262.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Облицювання укосів мозаїчною плиткою",
			unit = WorkUnit.M2,
			minPrice = 800.0,
			maxPrice = 2000.0,
			averagePrice = 1306.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Монтаж декоративного панно з керамічної плитки",
			unit = WorkUnit.M2,
			minPrice = 1500.0,
			maxPrice = 2563.0,
			averagePrice = 2197.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання фриза з плитки (до 20 см)",
			unit = WorkUnit.MPOG,
			minPrice = 250.0,
			maxPrice = 650.0,
			averagePrice = 374.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Облицювання плиткою укосу, підвіконня",
			unit = WorkUnit.MPOG,
			minPrice = 400.0,
			maxPrice = 500.0,
			averagePrice = 433.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Безшовна укладання плитки",
			unit = WorkUnit.M2,
			minPrice = 690.0,
			maxPrice = 1600.0,
			averagePrice = 1117.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання клінкерної плитки",
			unit = WorkUnit.M2,
			minPrice = 550.0,
			maxPrice = 1500.0,
			averagePrice = 977.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання декоративного каменю",
			unit = WorkUnit.M2,
			minPrice = 600.0,
			maxPrice = 1500.0,
			averagePrice = 1061.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання плитки на ступені - радіусні",
			unit = WorkUnit.MPOG,
			minPrice = 700.0,
			maxPrice = 1500.0,
			averagePrice = 1095.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання плитки на ступені - прямі",
			unit = WorkUnit.MPOG,
			minPrice = 470.0,
			maxPrice = 1000.0,
			averagePrice = 790.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання підрізаної з торців (ректифікованої) плитки",
			unit = WorkUnit.M2,
			minPrice = 500.0,
			maxPrice = 1100.0,
			averagePrice = 828.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Монтаж капіносів ступенів",
			unit = WorkUnit.MPOG,
			minPrice = 500.0,
			maxPrice = 758.0,
			averagePrice = 657.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання мармуру або граніту",
			unit = WorkUnit.M2,
			minPrice = 800.0,
			maxPrice = 2000.0,
			averagePrice = 1244.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання керамограніта товщиною від 15 мм",
			unit = WorkUnit.M2,
			minPrice = 750.0,
			maxPrice = 1500.0,
			averagePrice = 1012.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Укладання керамічного паркету до 1200 мм",
			unit = WorkUnit.M2,
			minPrice = 700.0,
			maxPrice = 1300.0,
			averagePrice = 929.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Затирка швів плитки (напівсуха)",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 200.0,
			averagePrice = 123.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Затирка швів плитки (Двокомпонентна епоксидна)",
			unit = WorkUnit.MPOG,
			minPrice = 200.0,
			maxPrice = 300.0,
			averagePrice = 233.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Затирка швів плитки (герметична)",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 230.0,
			averagePrice = 163.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.TILING,
			name = "Облицювання плиткою короба",
			unit = WorkUnit.MPOG,
			minPrice = 500.0,
			maxPrice = 1000.0,
			averagePrice = 745.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		//endregion
		//region Малярні роботи
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Фарбування стін водоемульсійною фарбою",
			unit = WorkUnit.M2,
			minPrice = 80.0,
			maxPrice = 200.0,
			averagePrice = 141.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Фарбування стелі водоемульсійною фарбою",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 230.0,
			averagePrice = 157.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Фарбування стелі фарбопультом (пульвер)",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 220.0,
			averagePrice = 164.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Фарбування стін фарбопультом (пульвер)",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 200.0,
			averagePrice = 157.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Грунтовка під стяжку",
			unit = WorkUnit.M2,
			minPrice = 20.0,
			maxPrice = 40.0,
			averagePrice = 30.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Грунтовка стяжки під плитку",
			unit = WorkUnit.M2,
			minPrice = 20.0,
			maxPrice = 40.0,
			averagePrice = 29.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Грунтовка стін, стелі (1 шар)",
			unit = WorkUnit.M2,
			minPrice = 20.0,
			maxPrice = 50.0,
			averagePrice = 31.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Установка малярської сітки на стіни або стелю",
			unit = WorkUnit.M2,
			minPrice = 60.0,
			maxPrice = 150.0,
			averagePrice = 104.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Підготовка г. к. стелі під фарбування або поклейку шпалер",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 550.0,
			averagePrice = 341.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Підготовка г. к. стіни під фарбування або шпалери",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 450.0,
			averagePrice = 307.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Підготовка бетонної стелі под фарбування або шпалери",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 650.0,
			averagePrice = 422.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Підготовка бетонної стіни під фарбування або шпалери",
			unit = WorkUnit.M2,
			minPrice = 220.0,
			maxPrice = 650.0,
			averagePrice = 386.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Підготовка укосів під фарбування",
			unit = WorkUnit.MPOG,
			minPrice = 180.0,
			maxPrice = 450.0,
			averagePrice = 298.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Посилення зовнішніх кутів перфорованим куточком",
			unit = WorkUnit.MPOG,
			minPrice = 45.0,
			maxPrice = 120.0,
			averagePrice = 80.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Оздоблення міжрівневих торців стелі",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 350.0,
			averagePrice = 259.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Оздоблення ніш прихованого світла",
			unit = WorkUnit.MPOG,
			minPrice = 200.0,
			maxPrice = 550.0,
			averagePrice = 325.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Оздоблення укосів",
			unit = WorkUnit.MPOG,
			minPrice = 190.0,
			maxPrice = 450.0,
			averagePrice = 296.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Грунтовка і фарбування водопровідних труб",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 180.0,
			averagePrice = 126.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Монтаж пінопластового стельового багета c фарбуванням",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 300.0,
			averagePrice = 169.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Фарбування вікна",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 500.0,
			averagePrice = 354.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Наклейка скловолокна",
			unit = WorkUnit.M2,
			minPrice = 75.0,
			maxPrice = 200.0,
			averagePrice = 129.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Безповітряне фарбування",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 165.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Армування стрічкою стику на стіні або стелі",
			unit = WorkUnit.MPOG,
			minPrice = 45.0,
			maxPrice = 100.0,
			averagePrice = 76.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Закладення швів гіпсокартонних конструкцій",
			unit = WorkUnit.M2,
			minPrice = 45.0,
			maxPrice = 140.0,
			averagePrice = 80.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Монтаж настінних декорів, карнизів, молдингів з поліуретану",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 250.0,
			averagePrice = 189.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Шпаклівка, фарбування багета (поліуретан)",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 200.0,
			averagePrice = 162.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Фарбування дерев’яних поверхонь",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 174.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Герметизація акрилом примикання плитки до стелі",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 150.0,
			averagePrice = 112.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Монтаж на стелю поліуретан-багета c фарбуванням і шпаклівкою",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 350.0,
			averagePrice = 249.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Монтаж на стелю поліуретан-молдингу c фарбуванням і шпаклівкою",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 350.0,
			averagePrice = 259.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PAINTING,
			name = "Фарбування відкосів",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 200.0,
			averagePrice = 135.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		//endregion
		//region Штукатурні роботи
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Стартова штукатурка (стіна по маяках або стеля під правило)",
			unit = WorkUnit.M2,
			minPrice = 160.0,
			maxPrice = 450.0,
			averagePrice = 299.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Ручна Маякова штукатурка (до 2 см)",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 485.0,
			averagePrice = 317.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Ручна Маякова штукатурка (більше 2 см)",
			unit = WorkUnit.M2,
			minPrice = 220.0,
			maxPrice = 500.0,
			averagePrice = 355.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Ручна штукатурка укосів",
			unit = WorkUnit.MPOG,
			minPrice = 180.0,
			maxPrice = 450.0,
			averagePrice = 309.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Ручна штукатурка кривих поверхонь",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 353.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Ручна штукатурка ніш під радіатори",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 500.0,
			averagePrice = 376.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Машинна штукатурка стін",
			unit = WorkUnit.M2,
			minPrice = 210.0,
			maxPrice = 400.0,
			averagePrice = 298.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Машинна штукатурка стель",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 650.0,
			averagePrice = 449.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Грунтовка стін, стелі (1 шар) під штукатурку",
			unit = WorkUnit.M2,
			minPrice = 20.0,
			maxPrice = 50.0,
			averagePrice = 32.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Набивання (монтаж) штукатурної сітки",
			unit = WorkUnit.M2,
			minPrice = 70.0,
			maxPrice = 200.0,
			averagePrice = 119.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Машинна штукатурка укосів",
			unit = WorkUnit.MPOG,
			minPrice = 190.0,
			maxPrice = 380.0,
			averagePrice = 296.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Машинне нанесення фінішної шпаклівки",
			unit = WorkUnit.M2,
			minPrice = 150.0,
			maxPrice = 380.0,
			averagePrice = 273.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Монтаж маяків з розміткою площин на стелі",
			unit = WorkUnit.M2,
			minPrice = 60.0,
			maxPrice = 150.0,
			averagePrice = 108.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.PLASTERING,
			name = "Грунтовка бетоноконтактом",
			unit = WorkUnit.M2,
			minPrice = 50.0,
			maxPrice = 100.0,
			averagePrice = 74.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Монтаж гіпсокартону
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Монтаж гіпсокартону на стіну",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 500.0,
			averagePrice = 324.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Монтаж гіпсокартону на стелю",
			unit = WorkUnit.M2,
			minPrice = 240.0,
			maxPrice = 620.0,
			averagePrice = 407.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Монтаж перегородок з гіпсокартону (2 сторони)",
			unit = WorkUnit.M2,
			minPrice = 280.0,
			maxPrice = 800.0,
			averagePrice = 498.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Монтаж напівкруглої конструкцій (стіни) з гіпсокартону",
			unit = WorkUnit.M2,
			minPrice = 350.0,
			maxPrice = 900.0,
			averagePrice = 560.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Монтаж напівкруглої構造 (перегородки) з гісокартона",
			unit = WorkUnit.M2,
			minPrice = 350.0,
			maxPrice = 1000.0,
			averagePrice = 629.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Облаштування декор. елементів з гіпсокартону (Ніш, арок та ін.)",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 1500.0,
			averagePrice = 1130.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Монтаж укосів з гіпсокартону",
			unit = WorkUnit.MPOG,
			minPrice = 180.0,
			maxPrice = 500.0,
			averagePrice = 314.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Облаштування г / к стелі багаторівневого, прямого",
			unit = WorkUnit.MPOG,
			minPrice = 250.0,
			maxPrice = 600.0,
			averagePrice = 432.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Облаштування г / к стелі багаторівневого, півколо",
			unit = WorkUnit.MPOG,
			minPrice = 300.0,
			maxPrice = 700.0,
			averagePrice = 491.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Облаштування гіпсокартонного короба по периметру",
			unit = WorkUnit.MPOG,
			minPrice = 250.0,
			maxPrice = 550.0,
			averagePrice = 399.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Облаштування гіпсокартонного короба по периметру (півколо)",
			unit = WorkUnit.MPOG,
			minPrice = 300.0,
			maxPrice = 600.0,
			averagePrice = 443.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Облаштування ніші в стелі з гіпсокартону з підсвічуванням",
			unit = WorkUnit.MPOG,
			minPrice = 300.0,
			maxPrice = 700.0,
			averagePrice = 504.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Обшивка труб гіпсокартоном",
			unit = WorkUnit.MPOG,
			minPrice = 250.0,
			maxPrice = 550.0,
			averagePrice = 397.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Утеплення гіпсокартону мінватою в один шар",
			unit = WorkUnit.M2,
			minPrice = 70.0,
			maxPrice = 200.0,
			averagePrice = 128.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Утеплення гіпсокартону стіродуром",
			unit = WorkUnit.M2,
			minPrice = 70.0,
			maxPrice = 180.0,
			averagePrice = 119.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Оздоблення г / к інсталяції в с / в",
			unit = WorkUnit.M2,
			minPrice = 1000.0,
			maxPrice = 3000.0,
			averagePrice = 1910.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Монтаж звукоізоляційної стрічки на профіль",
			unit = WorkUnit.MPOG,
			minPrice = 20.0,
			maxPrice = 50.0,
			averagePrice = 35.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.DRYWALL,
			name = "Звукоізоляція стіни або стелі Tecsound s70",
			unit = WorkUnit.M2,
			minPrice = 185.0,
			maxPrice = 300.0,
			averagePrice = 251.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
//endregion
		//region Поклейка шпалер
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка паперових шпалер на стіну",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 220.0,
			averagePrice = 166.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка паперових шпалер на стелю",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 250.0,
			averagePrice = 184.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка шпалер на вініловій або флізеліновій основі на стіну",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 220.0,
			averagePrice = 164.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка шпалер на вініловій або флізеліновій основі на стелю",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 270.0,
			averagePrice = 190.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка шпалер на тканинній основі або на шовкотрафаретній",
			unit = WorkUnit.M2,
			minPrice = 130.0,
			maxPrice = 400.0,
			averagePrice = 238.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка бамбукових шпалер на стіни",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 400.0,
			averagePrice = 257.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка бамбукових шпалер на стелю",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 450.0,
			averagePrice = 298.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка скло шпалер на стіни",
			unit = WorkUnit.M2,
			minPrice = 110.0,
			maxPrice = 200.0,
			averagePrice = 159.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка скло шпалер на стелю",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 250.0,
			averagePrice = 181.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка пробкових шпалер на стіни",
			unit = WorkUnit.M2,
			minPrice = 145.0,
			maxPrice = 300.0,
			averagePrice = 218.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка пробкових шпалер на стелю",
			unit = WorkUnit.M2,
			minPrice = 170.0,
			maxPrice = 450.0,
			averagePrice = 275.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Поклейка фото шпалер на стіни",
			unit = WorkUnit.M2,
			minPrice = 170.0,
			maxPrice = 500.0,
			averagePrice = 288.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALLPAPER,
			name = "Нанесення рідких шпалер",
			unit = WorkUnit.M2,
			minPrice = 160.0,
			maxPrice = 400.0,
			averagePrice = 244.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Чорнові роботи по підлозі
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Заливка підлоги самовирівнюється розчином",
			unit = WorkUnit.M2,
			minPrice = 150.0,
			maxPrice = 330.0,
			averagePrice = 238.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Цементно-піщана стяжка до 5 см",
			unit = WorkUnit.M2,
			minPrice = 160.0,
			maxPrice = 400.0,
			averagePrice = 296.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Цементно-піщана стяжка 5-10 см",
			unit = WorkUnit.M2,
			minPrice = 220.0,
			maxPrice = 550.0,
			averagePrice = 379.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Нанесення грунтовки, 2 шари",
			unit = WorkUnit.M2,
			minPrice = 30.0,
			maxPrice = 80.0,
			averagePrice = 51.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Стяжка підлоги з керамзитом",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 368.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Монтаж плівкової гідроізоляції для стяжки",
			unit = WorkUnit.M2,
			minPrice = 30.0,
			maxPrice = 70.0,
			averagePrice = 49.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Армування стяжки підлоги",
			unit = WorkUnit.M2,
			minPrice = 50.0,
			maxPrice = 150.0,
			averagePrice = 88.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Утеплення підлоги (стіродуром) на стяжку",
			unit = WorkUnit.M2,
			minPrice = 59.0,
			maxPrice = 150.0,
			averagePrice = 105.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Монтаж армуючої сітки для стяжки",
			unit = WorkUnit.M2,
			minPrice = 50.0,
			maxPrice = 120.0,
			averagePrice = 84.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Облаштування машинної стяжки самовирівнюючими сумішами",
			unit = WorkUnit.M2,
			minPrice = 170.0,
			maxPrice = 320.0,
			averagePrice = 263.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Установка маяків для стяжки на підлогу",
			unit = WorkUnit.M2,
			minPrice = 50.0,
			maxPrice = 120.0,
			averagePrice = 89.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Шліфування стяжки підлоги",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 270.0,
			averagePrice = 161.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.ROUGH_FLOORS,
			name = "Стяжка з пінополістиролбетону",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 347.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		//endregion
		//region Покриття для підлоги
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання ламінату",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 300.0,
			averagePrice = 216.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання лінолеуму або килимового покриття без клею",
			unit = WorkUnit.M2,
			minPrice = 80.0,
			maxPrice = 230.0,
			averagePrice = 144.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання паркетної дошки на підлогу (клей)",
			unit = WorkUnit.M2,
			minPrice = 220.0,
			maxPrice = 500.0,
			averagePrice = 362.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання тришарової паркетної дошки на підлогу",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 600.0,
			averagePrice = 391.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Установка пластикових плінтусів",
			unit = WorkUnit.MPOG,
			minPrice = 60.0,
			maxPrice = 170.0,
			averagePrice = 106.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж МДФ плінтусів",
			unit = WorkUnit.MPOG,
			minPrice = 125.0,
			maxPrice = 270.0,
			averagePrice = 192.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж дерев'яного плінтуса",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 260.0,
			averagePrice = 182.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання фанери на підлогу",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 210.0,
			averagePrice = 163.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Влаштування підлоги з ламінованого паркету",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 316.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж перехідної планки прямий",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 168.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж перехідної радіусної планки",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 400.0,
			averagePrice = 243.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Установка обмежувача двері",
			unit = WorkUnit.PCS,
			minPrice = 95.0,
			maxPrice = 200.0,
			averagePrice = 155.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Циклювання паркету + покриття лаком",
			unit = WorkUnit.M2,
			minPrice = 450.0,
			maxPrice = 1000.0,
			averagePrice = 659.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж дерев'яної підлоги зі шпунтом",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 500.0,
			averagePrice = 371.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Брашіровка паркету",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 280.0,
			averagePrice = 243.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Художній паркет",
			unit = WorkUnit.M2,
			minPrice = 710.0,
			maxPrice = 1000.0,
			averagePrice = 891.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж плінтуса з поліуретану",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 280.0,
			averagePrice = 190.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання чорнової підлоги з фанери або OSB",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 300.0,
			averagePrice = 166.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Очищення підлоги від пилу і сміття",
			unit = WorkUnit.M2,
			minPrice = 30.0,
			maxPrice = 60.0,
			averagePrice = 45.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання ламінату по діагоналі",
			unit = WorkUnit.M2,
			minPrice = 150.0,
			maxPrice = 300.0,
			averagePrice = 236.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Облаштування вінілової плитки LG Decotile",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 400.0,
			averagePrice = 288.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Лакування паркету (в 3 шари), з шпаклівкою щілин",
			unit = WorkUnit.M2,
			minPrice = 260.0,
			maxPrice = 600.0,
			averagePrice = 397.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Облаштування комерційного лінолеуму на клей",
			unit = WorkUnit.M2,
			minPrice = 110.0,
			maxPrice = 300.0,
			averagePrice = 197.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Зварювання швів комерційного лінолеуму",
			unit = WorkUnit.M2,
			minPrice = 70.0,
			maxPrice = 126.0,
			averagePrice = 103.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Фарбування плінтуса",
			unit = WorkUnit.MPOG,
			minPrice = 60.0,
			maxPrice = 150.0,
			averagePrice = 99.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Облаштування дерев'яних лаг",
			unit = WorkUnit.M2,
			minPrice = 130.0,
			maxPrice = 300.0,
			averagePrice = 225.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж підлогової дошки",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 350.0,
			averagePrice = 272.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Облаштування OSB або фанери на підлогу",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 270.0,
			averagePrice = 174.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Фарбування дощатих підлог",
			unit = WorkUnit.M2,
			minPrice = 90.0,
			maxPrice = 220.0,
			averagePrice = 143.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Лакування дощатої підлоги (зі шліфовкою)",
			unit = WorkUnit.M2,
			minPrice = 170.0,
			maxPrice = 400.0,
			averagePrice = 291.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Укладання лінолеуму або килимового покриття на клей",
			unit = WorkUnit.M2,
			minPrice = 145.0,
			maxPrice = 250.0,
			averagePrice = 195.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Шпаклівка, шліфування фанери на підлозі",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 300.0,
			averagePrice = 202.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.FLOORING,
			name = "Монтаж плінтуса з алюмінію",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 330.0,
			averagePrice = 251.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		//endregion
		//region Стеля
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Натяжні стелі",
			unit = WorkUnit.M2,
			minPrice = 300.0,
			maxPrice = 700.0,
			averagePrice = 507.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Підвісна стеля Армстронг",
			unit = WorkUnit.M2,
			minPrice = 170.0,
			maxPrice = 390.0,
			averagePrice = 238.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Облаштування OSB, фанери на стелю",
			unit = WorkUnit.M2,
			minPrice = 150.0,
			maxPrice = 440.0,
			averagePrice = 260.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Облаштування обрешітки стелі дерев'яною рейкою",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 250.0,
			averagePrice = 181.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Облаштування куточків ПВХ стелі",
			unit = WorkUnit.MPOG,
			minPrice = 50.0,
			maxPrice = 110.0,
			averagePrice = 86.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Облаштування пінопластового стельового багета на прямі поверхні (до 80мм)",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 170.0,
			averagePrice = 122.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Облаштування поліуретанового стельового багета на прямі поверхні (до 100мм)",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 160.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Монтаж молдингів стелі (до 80мм)",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 190.0,
			averagePrice = 136.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Шпаклівка, фарбування багета (пінопласт)",
			unit = WorkUnit.MPOG,
			minPrice = 65.0,
			maxPrice = 150.0,
			averagePrice = 108.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Облаштування ліпнини з гіпсу по периметру стелі",
			unit = WorkUnit.MPOG,
			minPrice = 250.0,
			maxPrice = 500.0,
			averagePrice = 350.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.CEILING,
			name = "Стелі Грильято",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 400.0,
			averagePrice = 272.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		//endregion
		//region Монтаж вагонки
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WAGONKA,
			name = "Монтаж дерев'яної вагонки на стіну",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 600.0,
			averagePrice = 340.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WAGONKA,
			name = "Монтаж дерев'яної вагонки на стелю",
			unit = WorkUnit.M2,
			minPrice = 240.0,
			maxPrice = 600.0,
			averagePrice = 383.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WAGONKA,
			name = "Обшивка стін пластиковою вагонкою",
			unit = WorkUnit.M2,
			minPrice = 170.0,
			maxPrice = 420.0,
			averagePrice = 294.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WAGONKA,
			name = "Обшивка стелі пластиковою вагонкою",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 600.0,
			averagePrice = 347.0,
			targetSurface = TargetSurface.CEILING_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WAGONKA,
			name = "Монтаж блок хауса",
			unit = WorkUnit.M2,
			minPrice = 280.0,
			maxPrice = 600.0,
			averagePrice = 427.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WAGONKA,
			name = "Монтаж терасної дошки",
			unit = WorkUnit.M2,
			minPrice = 280.0,
			maxPrice = 670.0,
			averagePrice = 460.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WAGONKA,
			name = "Монтаж фальш бруса",
			unit = WorkUnit.M2,
			minPrice = 244.0,
			maxPrice = 600.0,
			averagePrice = 420.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		//endregion
		//region Декоративне оздоблення стін
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Облаштування фактурної штукатурки на гіпсовій основі",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 430.0,
			averagePrice = 322.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Мармурова крихта",
			unit = WorkUnit.M2,
			minPrice = 240.0,
			maxPrice = 450.0,
			averagePrice = 361.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Механізоване нанесення камінцевої штукатурки (баранчик)",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 400.0,
			averagePrice = 322.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка американка",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 550.0,
			averagePrice = 360.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Венеціанська штукатурка",
			unit = WorkUnit.M2,
			minPrice = 350.0,
			maxPrice = 800.0,
			averagePrice = 561.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка з ефектом бетону",
			unit = WorkUnit.M2,
			minPrice = 350.0,
			maxPrice = 950.0,
			averagePrice = 589.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка Травертин",
			unit = WorkUnit.M2,
			minPrice = 400.0,
			maxPrice = 800.0,
			averagePrice = 524.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка Марморіно",
			unit = WorkUnit.M2,
			minPrice = 440.0,
			maxPrice = 1100.0,
			averagePrice = 726.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка короїд",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 450.0,
			averagePrice = 333.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка Гротто",
			unit = WorkUnit.M2,
			minPrice = 280.0,
			maxPrice = 500.0,
			averagePrice = 403.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка Марсельський віск",
			unit = WorkUnit.M2,
			minPrice = 350.0,
			maxPrice = 750.0,
			averagePrice = 567.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка Кракелюр",
			unit = WorkUnit.M2,
			minPrice = 350.0,
			maxPrice = 900.0,
			averagePrice = 592.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка Креос (Сахара)",
			unit = WorkUnit.M2,
			minPrice = 280.0,
			maxPrice = 550.0,
			averagePrice = 451.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_DECOR,
			name = "Штукатурка Отточенто",
			unit = WorkUnit.M2,
			minPrice = 280.0,
			maxPrice = 750.0,
			averagePrice = 459.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		//endregion
		//region Стінова панель
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_PANELS,
			name = "Монтаж настінних панелей ПВХ, МДФ по каркасу",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 330.0,
			averagePrice = 256.0,
			targetSurface = TargetSurface.WALL_CLEAN_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.WALL_PANELS,
			name = "Монтаж МДФ куточків",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 150.0,
			averagePrice = 122.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		//endregion
		//region Ліпнина
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Розетки з гіпсу",
			unit = WorkUnit.PCS,
			minPrice = 180.0,
			maxPrice = 300.0,
			averagePrice = 253.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Карнизи з гіпсу",
			unit = WorkUnit.MPOG,
			minPrice = 155.0,
			maxPrice = 350.0,
			averagePrice = 229.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Плінтуса з гіпсу",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 200.0,
			averagePrice = 183.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Молдинг з гіпсу",
			unit = WorkUnit.MPOG,
			minPrice = 170.0,
			maxPrice = 350.0,
			averagePrice = 230.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Колони з гіпсу",
			unit = WorkUnit.MPOG,
			minPrice = 200.0,
			maxPrice = 400.0,
			averagePrice = 333.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Кутовий гіпсовий елемент",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 400.0,
			averagePrice = 292.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Пілястри з гіпсу",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 750.0,
			averagePrice = 603.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Світильники точкові гіпсові",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 350.0,
			averagePrice = 270.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Ліпнина (монтаж за м2)",
			unit = WorkUnit.M2,
			minPrice = 400.0,
			maxPrice = 900.0,
			averagePrice = 662.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Ліпнина (монтаж за м.пог.)",
			unit = WorkUnit.MPOG,
			minPrice = 320.0,
			maxPrice = 700.0,
			averagePrice = 527.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STUCCO,
			name = "Позолота ліпнини",
			unit = WorkUnit.M2,
			minPrice = 300.0,
			maxPrice = 700.0,
			averagePrice = 467.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Розпис стін
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.MURALS,
			name = "Проста розпис (трафаретний)",
			unit = WorkUnit.M2,
			minPrice = 500.0,
			maxPrice = 980.0,
			averagePrice = 867.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.MURALS,
			name = "Нескладний розпис (графіка, логотипи)",
			unit = WorkUnit.M2,
			minPrice = 2260.0,
			maxPrice = 3100.0,
			averagePrice = 2602.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.MURALS,
			name = "Середній розпис (багато відтінків, небо)",
			unit = WorkUnit.M2,
			minPrice = 2500.0,
			maxPrice = 3850.0,
			averagePrice = 3196.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.MURALS,
			name = "Складна розпис (живопис, 3D, деталізація)",
			unit = WorkUnit.M2,
			minPrice = 3700.0,
			maxPrice = 3900.0,
			averagePrice = 3815.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.MURALS,
			name = "Дуже складна розпис (якість картини)",
			unit = WorkUnit.M2,
			minPrice = 3000.0,
			maxPrice = 5000.0,
			averagePrice = 4090.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Роботи з каменем, мармуром, гранітом
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Шліфування та полірування мармуру",
			unit = WorkUnit.M2,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Шліфування та полірування граніту",
			unit = WorkUnit.M2,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Кристалізація мармуру",
			unit = WorkUnit.M2,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Очищення мармуру та граніту",
			unit = WorkUnit.M2,
			minPrice = 413.0,
			maxPrice = 650.0,
			averagePrice = 521.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Реставрація мармуру або граніту",
			unit = WorkUnit.M2,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Свердління отворів",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 400.0,
			averagePrice = 300.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж підвіконня",
			unit = WorkUnit.MPOG,
			minPrice = 700.0,
			maxPrice = 971.0,
			averagePrice = 857.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж стільниць, стійок",
			unit = WorkUnit.MPOG,
			minPrice = 1678.0,
			maxPrice = 3000.0,
			averagePrice = 2226.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж плінтуса (камінь)",
			unit = WorkUnit.MPOG,
			minPrice = 350.0,
			maxPrice = 540.0,
			averagePrice = 421.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж сходів прямих (ступінь і присхідців)",
			unit = WorkUnit.MPOG,
			minPrice = 1210.0,
			maxPrice = 2500.0,
			averagePrice = 1737.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж плінтуса на підлозі",
			unit = WorkUnit.MPOG,
			minPrice = 300.0,
			maxPrice = 372.0,
			averagePrice = 334.0,
			targetSurface = TargetSurface.ROOM_PERIMETER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж (укладання) каменю на підлогу, стіни",
			unit = WorkUnit.M2,
			minPrice = 750.0,
			maxPrice = 1200.0,
			averagePrice = 1001.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж слебна великими модулями (від 1500х1500 мм)",
			unit = WorkUnit.M2,
			minPrice = 1500.0,
			maxPrice = 2500.0,
			averagePrice = 2033.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.STONE_WORKS,
			name = "Монтаж (облицювання) фасаду (вентильований)",
			unit = WorkUnit.M2,
			minPrice = 1250.0,
			maxPrice = 2500.0,
			averagePrice = 1683.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Промислові підлоги
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.INDUSTRIAL_FLOORS,
			name = "Полімерні промислові підлоги",
			unit = WorkUnit.M2,
			minPrice = 280.0,
			maxPrice = 430.0,
			averagePrice = 343.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.INDUSTRIAL_FLOORS,
			name = "Бетонні промислові підлоги з топінгом",
			unit = WorkUnit.M2,
			minPrice = 350.0,
			maxPrice = 550.0,
			averagePrice = 446.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.INDUSTRIAL_FLOORS,
			name = "Промислові підлоги з полірованого бетону",
			unit = WorkUnit.M2,
			minPrice = 320.0,
			maxPrice = 460.0,
			averagePrice = 390.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.INDUSTRIAL_FLOORS,
			name = "Промислові підлоги ПВХ плити",
			unit = WorkUnit.M2,
			minPrice = 150.0,
			maxPrice = 270.0,
			averagePrice = 220.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.INDUSTRIAL_FLOORS,
			name = "Виготовлення антистатичного полімерного покриття від 2 мм",
			unit = WorkUnit.M2,
			minPrice = 160.0,
			maxPrice = 300.0,
			averagePrice = 242.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.INDUSTRIAL_FLOORS,
			name = "Виготовлення промислового полімерного покриття типу налив від 2 мм",
			unit = WorkUnit.M2,
			minPrice = 160.0,
			maxPrice = 310.0,
			averagePrice = 232.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		WorkService(
			section = WorkSection.FINISHING,
			category = WorkCategory.INDUSTRIAL_FLOORS,
			name = "Знепилювання бетонної підлоги, кольорове полімерне покриття",
			unit = WorkUnit.M2,
			minPrice = 125.0,
			maxPrice = 310.0,
			averagePrice = 203.0,
			targetSurface = TargetSurface.FLOOR_AREA
		),
		//endregion
		//endregion ОЗДОБЛЮВАЛЬНІ РОБОТИ

		//region ІНЖЕНЕРНІ СИСТЕМИ ТА КОМУНІКАЦІЇ
		//region Електромонтажні роботи
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Штроблення під проводку в бетоні (глибина 2 см)",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 166.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Штроблення під проводку в цеглі (глибина 2 см)",
			unit = WorkUnit.MPOG,
			minPrice = 70.0,
			maxPrice = 170.0,
			averagePrice = 121.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Облаштування ніші (цегла)",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1200.0,
			averagePrice = 803.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Облаштування ніші (бетон)",
			unit = WorkUnit.PCS,
			minPrice = 900.0,
			maxPrice = 2200.0,
			averagePrice = 1430.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка електрощитка",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 1000.0,
			averagePrice = 657.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Підключення електролічильника",
			unit = WorkUnit.PCS,
			minPrice = 350.0,
			maxPrice = 1000.0,
			averagePrice = 634.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка автоматів (1 фаза)",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 166.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж автомата",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 160.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка силових вимикачів, УЗО",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 326.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Вирізка отвору і установка распредкоробки (бетон)",
			unit = WorkUnit.PCS,
			minPrice = 130.0,
			maxPrice = 400.0,
			averagePrice = 243.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Вирізка отвору і установка распредкоробки (цегла)",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 171.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Вирізка отвору і установка распредкоробки (гіпсокартон)",
			unit = WorkUnit.PCS,
			minPrice = 80.0,
			maxPrice = 200.0,
			averagePrice = 129.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Збірка распредкоробки, розпаювання проводів",
			unit = WorkUnit.PCS,
			minPrice = 170.0,
			maxPrice = 430.0,
			averagePrice = 308.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Підведення дроту і його закріплення (відкрита проводка)",
			unit = WorkUnit.MPOG,
			minPrice = 25.0,
			maxPrice = 55.0,
			averagePrice = 39.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Підведення дроту і його закріплення (гофротруба)",
			unit = WorkUnit.MPOG,
			minPrice = 35.0,
			maxPrice = 75.0,
			averagePrice = 53.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Підведення кабелю перетином вище 4 кв.мм (відкрита проводка)",
			unit = WorkUnit.MPOG,
			minPrice = 30.0,
			maxPrice = 73.0,
			averagePrice = 51.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Підведення кабелю перетином вище 10 кв.мм (відкрита проводка)",
			unit = WorkUnit.MPOG,
			minPrice = 40.0,
			maxPrice = 100.0,
			averagePrice = 65.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Вирізка отвору і установка коробки (бетон)",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 310.0,
			averagePrice = 216.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Вирізка отвору і установка коробки (цегла)",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 210.0,
			averagePrice = 158.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Вирізка отвору і установка коробки (гіпсокартон)",
			unit = WorkUnit.PCS,
			minPrice = 70.0,
			maxPrice = 180.0,
			averagePrice = 118.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка і підключення розеток і вимикачів",
			unit = WorkUnit.PCS,
			minPrice = 80.0,
			maxPrice = 160.0,
			averagePrice = 112.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж короба plasticового",
			unit = WorkUnit.MPOG,
			minPrice = 40.0,
			maxPrice = 100.0,
			averagePrice = 63.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка і підключення світильника Армстронг",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 400.0,
			averagePrice = 267.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка люстри",
			unit = WorkUnit.PCS,
			minPrice = 260.0,
			maxPrice = 700.0,
			averagePrice = 465.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж світильника настінного, бра",
			unit = WorkUnit.PCS,
			minPrice = 180.0,
			maxPrice = 500.0,
			averagePrice = 311.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж точкового світильника (без трансформатора)",
			unit = WorkUnit.PCS,
			minPrice = 120.0,
			maxPrice = 300.0,
			averagePrice = 198.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка і підключення трансформатора",
			unit = WorkUnit.PCS,
			minPrice = 130.0,
			maxPrice = 350.0,
			averagePrice = 234.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж і підключення стабілізатора напруги",
			unit = WorkUnit.PCS,
			minPrice = 670.0,
			maxPrice = 1500.0,
			averagePrice = 1116.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка датчиків (слаботочка)",
			unit = WorkUnit.PCS,
			minPrice = 120.0,
			maxPrice = 320.0,
			averagePrice = 214.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Свердління наскрізних отворів в стіні до 25 мм",
			unit = WorkUnit.PCS,
			minPrice = 80.0,
			maxPrice = 200.0,
			averagePrice = 124.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Облаштування контуру заземлення (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 4500.0,
			maxPrice = 8500.0,
			averagePrice = 6414.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Демонтаж силового кабелю",
			unit = WorkUnit.MPOG,
			minPrice = 60.0,
			maxPrice = 150.0,
			averagePrice = 110.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Демонтаж відкритої електропроводки",
			unit = WorkUnit.MPOG,
			minPrice = 10.0,
			maxPrice = 25.0,
			averagePrice = 16.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Демонтаж інших кабелів/проводки",
			unit = WorkUnit.MPOG,
			minPrice = 10.0,
			maxPrice = 25.0,
			averagePrice = 16.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Відключення і демонтаж точок (розетки, вимикачі, лампи)",
			unit = WorkUnit.PCS,
			minPrice = 35.0,
			maxPrice = 70.0,
			averagePrice = 52.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Перенесення електрощитка in квартиру",
			unit = WorkUnit.PCS,
			minPrice = 1300.0,
			maxPrice = 3000.0,
			averagePrice = 2121.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Заміна електропроводки в 1-кімнатній квартирі (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 14000.0,
			maxPrice = 35000.0,
			averagePrice = 26352.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Заміна електропроводки в 2-кімнатній квартирі (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 18000.0,
			maxPrice = 45000.0,
			averagePrice = 32889.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Заміна електропроводки в 3-кімнатній квартирі (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 25000.0,
			maxPrice = 65000.0,
			averagePrice = 42333.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Заміна електропроводки в 4-кімнатній квартирі (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 30000.0,
			maxPrice = 85000.0,
			averagePrice = 53007.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка світильників ландшафтних в грунт",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 550.0,
			averagePrice = 384.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка світильників в ступені (бетон)",
			unit = WorkUnit.PCS,
			minPrice = 280.0,
			maxPrice = 555.0,
			averagePrice = 390.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка світильників грунтових (без бетону)",
			unit = WorkUnit.PCS,
			minPrice = 180.0,
			maxPrice = 400.0,
			averagePrice = 293.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка світильників підводних",
			unit = WorkUnit.PCS,
			minPrice = 235.0,
			maxPrice = 520.0,
			averagePrice = 390.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка та підключення прожектора фасаду",
			unit = WorkUnit.PCS,
			minPrice = 220.0,
			maxPrice = 650.0,
			averagePrice = 399.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка світлодіодних світильників",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 400.0,
			averagePrice = 246.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Розробка схеми електропроводки",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 260.0,
			averagePrice = 159.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Штроблення стіни під проводку в бетоні (загальне)",
			unit = WorkUnit.MPOG,
			minPrice = 88.0,
			maxPrice = 250.0,
			averagePrice = 158.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Штроблення стіни під провід в цеглі (загальне)",
			unit = WorkUnit.MPOG,
			minPrice = 65.0,
			maxPrice = 180.0,
			averagePrice = 117.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Штроблення стіни під провід в гіпсокартоні",
			unit = WorkUnit.MPOG,
			minPrice = 50.0,
			maxPrice = 120.0,
			averagePrice = 76.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка врізного або канального вентилятора",
			unit = WorkUnit.PCS,
			minPrice = 220.0,
			maxPrice = 600.0,
			averagePrice = 378.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка кондиціонера електриком",
			unit = WorkUnit.PCS,
			minPrice = 2200.0,
			maxPrice = 5500.0,
			averagePrice = 3915.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж датчика системи Нептун",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 600.0,
			averagePrice = 416.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Підключення блоку управління системи Нептун",
			unit = WorkUnit.PCS,
			minPrice = 900.0,
			maxPrice = 2100.0,
			averagePrice = 1450.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж розетки СКС",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 250.0,
			averagePrice = 190.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Прозвонка проводки в приміщенні",
			unit = WorkUnit.M2,
			minPrice = 55.0,
			maxPrice = 150.0,
			averagePrice = 106.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж кнопки дзвінка",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 300.0,
			averagePrice = 179.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Встановлення та підключення вхідного дзвінка",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 400.0,
			averagePrice = 254.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж і установка домофона",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 1800.0,
			averagePrice = 1207.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж стрічки LED освітлення в коробі",
			unit = WorkUnit.MPOG,
			minPrice = 150.0,
			maxPrice = 360.0,
			averagePrice = 228.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж і підключення рушникосушки електричної",
			unit = WorkUnit.PCS,
			minPrice = 450.0,
			maxPrice = 1200.0,
			averagePrice = 698.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Облаштування отвору під вентилятор",
			unit = WorkUnit.PCS,
			minPrice = 300.0,
			maxPrice = 550.0,
			averagePrice = 448.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Закладення штроби",
			unit = WorkUnit.MPOG,
			minPrice = 30.0,
			maxPrice = 85.0,
			averagePrice = 54.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж електричних точок по бетону (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 329.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж електричних точок по цеглі (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 400.0,
			averagePrice = 262.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж електричних точок в гіпсокартоні (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 300.0,
			averagePrice = 212.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Установка автомата (3 фази)",
			unit = WorkUnit.PCS,
			minPrice = 270.0,
			maxPrice = 680.0,
			averagePrice = 418.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Заміна електричної розетки (демонтаж + монтаж)",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 300.0,
			averagePrice = 179.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Встановлення реле напруги",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 630.0,
			averagePrice = 419.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELECTRIC,
			name = "Монтаж та підключення ДБЖ",
			unit = WorkUnit.PCS,
			minPrice = 8000.0,
			maxPrice = 13000.0,
			averagePrice = 11321.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Сантехнічні роботи
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Штроблення під труби в бетоні (глибина 5 см)",
			unit = WorkUnit.MPOG,
			minPrice = 169.0,
			maxPrice = 450.0,
			averagePrice = 301.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Штроблення під труби в цеглі (глибина 5 см)",
			unit = WorkUnit.MPOG,
			minPrice = 130.0,
			maxPrice = 350.0,
			averagePrice = 227.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Демонтаж ванни",
			unit = WorkUnit.PCS,
			minPrice = 395.0,
			maxPrice = 1000.0,
			averagePrice = 643.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Демонтаж каналізації",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 150.0,
			averagePrice = 105.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Демонтаж сталевих труб х/г води",
			unit = WorkUnit.MPOG,
			minPrice = 49.0,
			maxPrice = 120.0,
			averagePrice = 82.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Демонтаж умивальника",
			unit = WorkUnit.PCS,
			minPrice = 170.0,
			maxPrice = 350.0,
			averagePrice = 252.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Демонтаж унітазу",
			unit = WorkUnit.PCS,
			minPrice = 220.0,
			maxPrice = 500.0,
			averagePrice = 372.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Заміна арматури в бачку унітазу",
			unit = WorkUnit.PCS,
			minPrice = 339.0,
			maxPrice = 800.0,
			averagePrice = 543.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Заміна гнучкої підводки",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 300.0,
			averagePrice = 213.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Переварювання стояків (заміна труб)",
			unit = WorkUnit.MPOG,
			minPrice = 1150.0,
			maxPrice = 2500.0,
			averagePrice = 1972.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Прокладка металопластикових труб водопостачання Ø16-40",
			unit = WorkUnit.MPOG,
			minPrice = 45.0,
			maxPrice = 100.0,
			averagePrice = 76.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Прокладка поліпропіленових труб водопостачання Ø16-40",
			unit = WorkUnit.MPOG,
			minPrice = 50.0,
			maxPrice = 100.0,
			averagePrice = 80.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Прокладка мідних труб водопостачання",
			unit = WorkUnit.MPOG,
			minPrice = 140.0,
			maxPrice = 400.0,
			averagePrice = 240.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Врізка в стояк (1 точка врізу)",
			unit = WorkUnit.PCS,
			minPrice = 600.0,
			maxPrice = 1500.0,
			averagePrice = 983.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Ізоляція труб",
			unit = WorkUnit.MPOG,
			minPrice = 25.0,
			maxPrice = 60.0,
			averagePrice = 41.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Заміна стояка каналізації в межах 1 поверху",
			unit = WorkUnit.PCS,
			minPrice = 2499.0,
			maxPrice = 6000.0,
			averagePrice = 3819.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Внутрішня розводка труб каналізації 50мм",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 170.0,
			averagePrice = 129.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Розводка труб каналізації (1 елемент з'єднання)",
			unit = WorkUnit.PCS,
			minPrice = 80.0,
			maxPrice = 170.0,
			averagePrice = 117.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка ванни (без змішувача)",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 4000.0,
			averagePrice = 2778.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка унітазу або біде",
			unit = WorkUnit.PCS,
			minPrice = 699.0,
			maxPrice = 1500.0,
			averagePrice = 1135.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка умивальника або мийки",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1100.0,
			averagePrice = 849.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка змішувача (ванна, умивальник, біде, душ)",
			unit = WorkUnit.PCS,
			minPrice = 399.0,
			maxPrice = 1000.0,
			averagePrice = 639.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка і підключення бойлера",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 2500.0,
			averagePrice = 1740.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка і підключення пральної/посудомийної машини",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 1000.0,
			averagePrice = 690.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка лічильника води",
			unit = WorkUnit.PCS,
			minPrice = 499.0,
			maxPrice = 1250.0,
			averagePrice = 812.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Зварна точка",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 850.0,
			averagePrice = 699.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Зварний шов",
			unit = WorkUnit.MPOG,
			minPrice = 350.0,
			maxPrice = 700.0,
			averagePrice = 573.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Точка холодної води",
			unit = WorkUnit.PCS,
			minPrice = 350.0,
			maxPrice = 1000.0,
			averagePrice = 612.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Точка гарячої води",
			unit = WorkUnit.PCS,
			minPrice = 350.0,
			maxPrice = 1000.0,
			averagePrice = 607.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Точка каналізації",
			unit = WorkUnit.PCS,
			minPrice = 350.0,
			maxPrice = 800.0,
			averagePrice = 564.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Облаштування стояків",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 3500.0,
			averagePrice = 2451.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж колектора",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 3000.0,
			averagePrice = 2264.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж гідроакумулятора",
			unit = WorkUnit.PCS,
			minPrice = 650.0,
			maxPrice = 1500.0,
			averagePrice = 1212.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж простого фільтра очищення води осмос",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1700.0,
			averagePrice = 1228.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Расчеканка чавунної каналізації",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 2000.0,
			averagePrice = 1446.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж сололіфт",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 4000.0,
			averagePrice = 2454.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж гігієнічного душу",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 2000.0,
			averagePrice = 1289.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж кухонного подрібнювача",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 2000.0,
			averagePrice = 1199.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж мультибокса (вбудований змішувач)",
			unit = WorkUnit.PCS,
			minPrice = 1250.0,
			maxPrice = 3100.0,
			averagePrice = 2135.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка душової штанги з лійкою",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 1000.0,
			averagePrice = 690.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка сифона",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 600.0,
			averagePrice = 425.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка інсталяції",
			unit = WorkUnit.PCS,
			minPrice = 1100.0,
			maxPrice = 3000.0,
			averagePrice = 1936.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка душового піддону",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 3000.0,
			averagePrice = 2073.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж трапа",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 2000.0,
			averagePrice = 1329.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка і підключення ванни з гідромасажем",
			unit = WorkUnit.PCS,
			minPrice = 3500.0,
			maxPrice = 8000.0,
			averagePrice = 5558.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж редуктора тиску",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1000.0,
			averagePrice = 732.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж фільтра самопромивного",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1200.0,
			averagePrice = 882.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж водозапірної арматури (кран, вентиль)",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 500.0,
			averagePrice = 391.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Заміна крана для води",
			unit = WorkUnit.PCS,
			minPrice = 999.0,
			maxPrice = 1500.0,
			averagePrice = 1154.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж каналізаційної насосної станції",
			unit = WorkUnit.PCS,
			minPrice = 2400.0,
			maxPrice = 5000.0,
			averagePrice = 3342.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Врізка в каналізаційний стояк",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1600.0,
			averagePrice = 1188.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж зворотного клапана каналізації",
			unit = WorkUnit.PCS,
			minPrice = 1300.0,
			maxPrice = 2500.0,
			averagePrice = 1743.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Механічна прочищення каналізації",
			unit = WorkUnit.MPOG,
			minPrice = 160.0,
			maxPrice = 300.0,
			averagePrice = 209.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Прочищення вертикальних труб каналізації (стояк)",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 2000.0,
			averagePrice = 1110.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Гідродинамічна прочищення каналізації",
			unit = WorkUnit.MPOG,
			minPrice = 1500.0,
			maxPrice = 2500.0,
			averagePrice = 1956.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Внутрішня розводка труб каналізації 110 мм",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 300.0,
			averagePrice = 198.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Прокладка металопластикових труб водопостачання Ø55-63",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 200.0,
			averagePrice = 153.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Прокладка поліпропіленових труб водопостачання Ø55-63",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 200.0,
			averagePrice = 155.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка навісного пісуара",
			unit = WorkUnit.PCS,
			minPrice = 650.0,
			maxPrice = 1500.0,
			averagePrice = 1195.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка сифона подвійного на кухню",
			unit = WorkUnit.PCS,
			minPrice = 399.0,
			maxPrice = 800.0,
			averagePrice = 640.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка проточного водонагрівача",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 2000.0,
			averagePrice = 1228.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка настінного змішувача",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 1000.0,
			averagePrice = 647.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка і підключення рушникосушки",
			unit = WorkUnit.PCS,
			minPrice = 750.0,
			maxPrice = 1500.0,
			averagePrice = 1209.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Установка душової кабіни (без змішувача)",
			unit = WorkUnit.PCS,
			minPrice = 1900.0,
			maxPrice = 5000.0,
			averagePrice = 3098.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж Біде на навісній консолі",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 2000.0,
			averagePrice = 1313.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Монтаж унітазу на навісній консолі з кнопкою",
			unit = WorkUnit.PCS,
			minPrice = 750.0,
			maxPrice = 1600.0,
			averagePrice = 1274.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.PLUMBING,
			name = "Забивання швів ванни або піддону (силікон)",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 200.0,
			averagePrice = 161.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		//endregion
		//region Системи опалення
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж котла підлогового газового до 55 кВт",
			unit = WorkUnit.PCS,
			minPrice = 3500.0,
			maxPrice = 6000.0,
			averagePrice = 4833.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Демонтаж підлогового котла",
			unit = WorkUnit.PCS,
			minPrice = 599.0,
			maxPrice = 1200.0,
			averagePrice = 864.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка батареї опалення",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 2000.0,
			averagePrice = 1247.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Підключення батареї",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 800.0,
			averagePrice = 585.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Прокладка металопластикових труб опалення Ø16-40",
			unit = WorkUnit.MPOG,
			minPrice = 50.0,
			maxPrice = 100.0,
			averagePrice = 74.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Прокладка поліпропіленових труб опалення Ø16-40",
			unit = WorkUnit.MPOG,
			minPrice = 50.0,
			maxPrice = 100.0,
			averagePrice = 75.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Прокладка мідних труб опалення",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 250.0,
			averagePrice = 185.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж стояків",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 2600.0,
			averagePrice = 2200.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж колектора",
			unit = WorkUnit.PCS,
			minPrice = 999.0,
			maxPrice = 2000.0,
			averagePrice = 1427.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Випробування системи тиском в 6 бар",
			unit = WorkUnit.PCS,
			minPrice = 900.0,
			maxPrice = 1500.0,
			averagePrice = 1100.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка настінного двоконтурного газового котла до 32 кВт",
			unit = WorkUnit.PCS,
			minPrice = 2300.0,
			maxPrice = 7000.0,
			averagePrice = 3750.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка настінного одноконтурного котла з бойлером до 32 кВт",
			unit = WorkUnit.PCS,
			minPrice = 3999.0,
			maxPrice = 5000.0,
			averagePrice = 4580.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка конденсаційного котла",
			unit = WorkUnit.PCS,
			minPrice = 2999.0,
			maxPrice = 6000.0,
			averagePrice = 4140.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка твердопаливного котла до 55 кВт",
			unit = WorkUnit.PCS,
			minPrice = 6699.0,
			maxPrice = 10000.0,
			averagePrice = 8220.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка пелетного котла (зі складанням бункера)",
			unit = WorkUnit.PCS,
			minPrice = 9000.0,
			maxPrice = 10999.0,
			averagePrice = 10166.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка електрокотла до 28 кВт",
			unit = WorkUnit.PCS,
			minPrice = 2499.0,
			maxPrice = 4500.0,
			averagePrice = 3200.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Запуск котла",
			unit = WorkUnit.PCS,
			minPrice = 650.0,
			maxPrice = 1600.0,
			averagePrice = 981.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж насоса",
			unit = WorkUnit.PCS,
			minPrice = 599.0,
			maxPrice = 1200.0,
			averagePrice = 914.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Промивання системи опалення",
			unit = WorkUnit.PCS,
			minPrice = 1050.0,
			maxPrice = 2500.0,
			averagePrice = 1642.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Запуск системи опалення",
			unit = WorkUnit.PCS,
			minPrice = 820.0,
			maxPrice = 2000.0,
			averagePrice = 1530.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Збірка димоходу котла в котельні",
			unit = WorkUnit.PCS,
			minPrice = 1100.0,
			maxPrice = 2000.0,
			averagePrice = 1550.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка електро датчика (бойлер, зовнішній, температур)",
			unit = WorkUnit.PCS,
			minPrice = 375.0,
			maxPrice = 600.0,
			averagePrice = 503.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка контролера або кімнатного термостата",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 700.0,
			averagePrice = 570.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка додаткового термостата в бойлер",
			unit = WorkUnit.PCS,
			minPrice = 355.0,
			maxPrice = 550.0,
			averagePrice = 461.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Прокладка труб зшитий поліетилен",
			unit = WorkUnit.MPOG,
			minPrice = 40.0,
			maxPrice = 75.0,
			averagePrice = 52.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Опресування фітингів зшитий поліетилен",
			unit = WorkUnit.M2,
			minPrice = 70.0,
			maxPrice = 120.0,
			averagePrice = 92.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж газової колонки",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 3000.0,
			averagePrice = 2014.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Перенесення радіатора опалення",
			unit = WorkUnit.PCS,
			minPrice = 1200.0,
			maxPrice = 3000.0,
			averagePrice = 2080.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж гідрострілки опалення",
			unit = WorkUnit.PCS,
			minPrice = 1450.0,
			maxPrice = 2800.0,
			averagePrice = 2004.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж бойлера непрямого нагріву до 300 л.",
			unit = WorkUnit.PCS,
			minPrice = 2000.0,
			maxPrice = 6000.0,
			averagePrice = 3271.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Обв'язка котельні міддю",
			unit = WorkUnit.PCS,
			minPrice = 48000.0,
			maxPrice = 55000.0,
			averagePrice = 50750.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж насосної групи",
			unit = WorkUnit.PCS,
			minPrice = 950.0,
			maxPrice = 1700.0,
			averagePrice = 1467.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж колектора опалення",
			unit = WorkUnit.PCS,
			minPrice = 1050.0,
			maxPrice = 2500.0,
			averagePrice = 2042.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж труби димоходу турбованого (без отвору)",
			unit = WorkUnit.PCS,
			minPrice = 650.0,
			maxPrice = 1000.0,
			averagePrice = 816.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Обв'язка котельні до 40 кВт під ключ",
			unit = WorkUnit.PCS,
			minPrice = 49000.0,
			maxPrice = 80000.0,
			averagePrice = 59333.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка і підключення Гребінки опалення до 5 контурів",
			unit = WorkUnit.PCS,
			minPrice = 1180.0,
			maxPrice = 3000.0,
			averagePrice = 2181.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка і підключення Гребінки понад 5 контурів",
			unit = WorkUnit.PCS,
			minPrice = 2150.0,
			maxPrice = 5000.0,
			averagePrice = 3231.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Прокладка металопластикових труб опалення Ø55-63",
			unit = WorkUnit.MPOG,
			minPrice = 130.0,
			maxPrice = 250.0,
			averagePrice = 194.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Прокладка металопластикових труб опалення Ø55-63 (варіант 2)",
			unit = WorkUnit.MPOG,
			minPrice = 99.0,
			maxPrice = 200.0,
			averagePrice = 126.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Прокладка поліпропіленових труб опалення Ø55-63",
			unit = WorkUnit.MPOG,
			minPrice = 99.0,
			maxPrice = 200.0,
			averagePrice = 146.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка настінного двоконтурного газового котла 32 -100 кВт",
			unit = WorkUnit.PCS,
			minPrice = 4999.0,
			maxPrice = 8000.0,
			averagePrice = 5820.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж котла підлогового газового 55-100кВт",
			unit = WorkUnit.PCS,
			minPrice = 5000.0,
			maxPrice = 10000.0,
			averagePrice = 6840.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка електрокотла 28-60 кВт",
			unit = WorkUnit.PCS,
			minPrice = 3300.0,
			maxPrice = 7000.0,
			averagePrice = 4925.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Установка твердопаливного котла 55-100 кВт",
			unit = WorkUnit.PCS,
			minPrice = 11300.0,
			maxPrice = 18000.0,
			averagePrice = 13060.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж бойлера непрямого нагріву понад 300 л.",
			unit = WorkUnit.PCS,
			minPrice = 4800.0,
			maxPrice = 7200.0,
			averagePrice = 5750.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.HEATING_SYSTEMS,
			name = "Монтаж буферної ємності для тт котла",
			unit = WorkUnit.PCS,
			minPrice = 5000.0,
			maxPrice = 8050.0,
			averagePrice = 6370.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Кліматичне обладнання
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Профілактика кондиціонера",
			unit = WorkUnit.PCS,
			minPrice = 950.0,
			maxPrice = 1650.0,
			averagePrice = 1123.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж кондиціонера 7-9-10-12",
			unit = WorkUnit.PCS,
			minPrice = 3000.0,
			maxPrice = 8000.0,
			averagePrice = 5340.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж магістралі (за 1 м)",
			unit = WorkUnit.MPOG,
			minPrice = 500.0,
			maxPrice = 1000.0,
			averagePrice = 770.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Повний демонтаж кондиціонера",
			unit = WorkUnit.PCS,
			minPrice = 1200.0,
			maxPrice = 2100.0,
			averagePrice = 1793.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж централізованої системи кондиціонування",
			unit = WorkUnit.PCS,
			minPrice = 3555.0,
			maxPrice = 7200.0,
			averagePrice = 5651.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Демонтаж внутрішнього блоку кондиціонера",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 1320.0,
			averagePrice = 1140.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж внутрішнього блоку кондиціонера",
			unit = WorkUnit.PCS,
			minPrice = 777.0,
			maxPrice = 1650.0,
			averagePrice = 1209.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Демонтаж зовнішнього блоку кондиціонера",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 1200.0,
			averagePrice = 1129.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж зовнішнього блоку спліт-системи",
			unit = WorkUnit.PCS,
			minPrice = 950.0,
			maxPrice = 1700.0,
			averagePrice = 1356.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж кондиціонера 15-18",
			unit = WorkUnit.PCS,
			minPrice = 3700.0,
			maxPrice = 6950.0,
			averagePrice = 5933.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж кондиціонера 24",
			unit = WorkUnit.PCS,
			minPrice = 4500.0,
			maxPrice = 8250.0,
			averagePrice = 6950.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Монтаж кондиціонера 30",
			unit = WorkUnit.PCS,
			minPrice = 5700.0,
			maxPrice = 8600.0,
			averagePrice = 7664.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Фанкойли настінні",
			unit = WorkUnit.PCS,
			minPrice = 1200.0,
			maxPrice = 1850.0,
			averagePrice = 1675.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.CLIMATE_EQUIPMENT,
			name = "Фанкойли стельові",
			unit = WorkUnit.PCS,
			minPrice = 1600.0,
			maxPrice = 2250.0,
			averagePrice = 2000.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Водопостачання та очищення води
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Виїзд фахівця на об'єкт (огляд, консультація, заміри) до 50км",
			unit = WorkUnit.PCS,
			minPrice = 550.0,
			maxPrice = 1500.0,
			averagePrice = 922.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Монтаж насосної станції",
			unit = WorkUnit.PCS,
			minPrice = 1200.0,
			maxPrice = 3000.0,
			averagePrice = 1947.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Монтаж автоматики для насосної станції",
			unit = WorkUnit.PCS,
			minPrice = 585.0,
			maxPrice = 1000.0,
			averagePrice = 847.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка погружного насоса (свердловина)",
			unit = WorkUnit.PCS,
			minPrice = 2999.0,
			maxPrice = 3500.0,
			averagePrice = 3158.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка глибинного насоса до 40м з облаштуванням",
			unit = WorkUnit.PCS,
			minPrice = 5000.0,
			maxPrice = 7099.0,
			averagePrice = 5925.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка глибинного насоса понад 40м з облаштуванням",
			unit = WorkUnit.PCS,
			minPrice = 10000.0,
			maxPrice = 12799.0,
			averagePrice = 11273.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка і підключення гідроакумулятора (до 300 л.)",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 2500.0,
			averagePrice = 2133.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Монтаж складної системи водоочищення",
			unit = WorkUnit.PCS,
			minPrice = 1650.0,
			maxPrice = 2850.0,
			averagePrice = 2260.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка однієї фільтруючої колони до 1354",
			unit = WorkUnit.PCS,
			minPrice = 7700.0,
			maxPrice = 12000.0,
			averagePrice = 10714.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка однієї фільтруючої колони від 1465 до 1665",
			unit = WorkUnit.PCS,
			minPrice = 7700.0,
			maxPrice = 13000.0,
			averagePrice = 11259.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка однієї фільтруючої колони від 1865 до 2162",
			unit = WorkUnit.PCS,
			minPrice = 12700.0,
			maxPrice = 17900.0,
			averagePrice = 15709.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка однієї фільтруючої колони понад 2162",
			unit = WorkUnit.PCS,
			minPrice = 12700.0,
			maxPrice = 18700.0,
			averagePrice = 16900.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Налаштування та перевірка керуючого клапана очищення води",
			unit = WorkUnit.PCS,
			minPrice = 1400.0,
			maxPrice = 2000.0,
			averagePrice = 1773.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Чистка та ремонт керуючого клапана без деталей",
			unit = WorkUnit.PCS,
			minPrice = 2000.0,
			maxPrice = 2770.0,
			averagePrice = 2257.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Хімічний аналіз води",
			unit = WorkUnit.PCS,
			minPrice = 745.0,
			maxPrice = 1000.0,
			averagePrice = 832.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Монтаж УФ знезаражувача води",
			unit = WorkUnit.PCS,
			minPrice = 1300.0,
			maxPrice = 2500.0,
			averagePrice = 2120.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Чистка та ремонт сольового бака фільтра",
			unit = WorkUnit.PCS,
			minPrice = 1300.0,
			maxPrice = 2300.0,
			averagePrice = 1844.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Завантаження / вивантаження фільтруючої системи кабінетного типу",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1300.0,
			averagePrice = 1051.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Завантаження / вивантаження фільтруючої системи колонного типу до 1354",
			unit = WorkUnit.PCS,
			minPrice = 600.0,
			maxPrice = 1300.0,
			averagePrice = 1016.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Діагностика та ремонт альтернатора фільтра очищення",
			unit = WorkUnit.PCS,
			minPrice = 600.0,
			maxPrice = 1000.0,
			averagePrice = 818.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Виїзд фахівця на об'єкт по місту",
			unit = WorkUnit.PCS,
			minPrice = 499.0,
			maxPrice = 750.0,
			averagePrice = 574.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Очищення бака сольового резервуара",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 800.0,
			averagePrice = 713.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Промивка іонообмінної смоли (без демонтажу)",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 900.0,
			averagePrice = 710.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Знезараження іонообмінної смоли гіпохлоридом натрію",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 820.0,
			averagePrice = 703.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Ремонт блоку управління фільтра очищення води",
			unit = WorkUnit.PCS,
			minPrice = 810.0,
			maxPrice = 1000.0,
			averagePrice = 915.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Чистка інжектора фільтра очищення води",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 660.0,
			averagePrice = 550.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка погружного насоса (колодязь)",
			unit = WorkUnit.PCS,
			minPrice = 1500.0,
			maxPrice = 3290.0,
			averagePrice = 2358.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Установка автоматики для погружного насоса (комплекс)",
			unit = WorkUnit.PCS,
			minPrice = 1300.0,
			maxPrice = 1599.0,
			averagePrice = 1474.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Обв'язка фільтра-колби механічного очищення з байпасом",
			unit = WorkUnit.PCS,
			minPrice = 1200.0,
			maxPrice = 1800.0,
			averagePrice = 1417.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.WATER_SUPPLY,
			name = "Обв'язка фільтра-колби механічного очищення без байпаса",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1300.0,
			averagePrice = 1058.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Тепла підлога
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Укладання електричної теплої підлоги",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 550.0,
			averagePrice = 379.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Монтаж матів інфрачервоної підлоги",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 450.0,
			averagePrice = 329.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Укладання труби теплої підлоги (стін) згідно з проектом",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 420.0,
			averagePrice = 319.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Установка та підключення термодатчика теплої підлоги",
			unit = WorkUnit.PCS,
			minPrice = 249.0,
			maxPrice = 500.0,
			averagePrice = 365.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Збірка і установка змішувального вузла для теплої підлоги",
			unit = WorkUnit.PCS,
			minPrice = 1200.0,
			maxPrice = 2500.0,
			averagePrice = 1807.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Укладання утеплювача для теплої підлоги",
			unit = WorkUnit.M3,
			minPrice = 550.0,
			maxPrice = 900.0,
			averagePrice = 756.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Установка кріплення труби (стіни) для теплої підлоги",
			unit = WorkUnit.PCS,
			minPrice = 70.0,
			maxPrice = 120.0,
			averagePrice = 92.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Установка відстінкової ізоляції і деформаційних швів",
			unit = WorkUnit.PCS,
			minPrice = 90.0,
			maxPrice = 160.0,
			averagePrice = 123.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Випробування системи теплої підлоги тиском 6 бар",
			unit = WorkUnit.PCS,
			minPrice = 770.0,
			maxPrice = 1250.0,
			averagePrice = 1014.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Пуск теплої підлоги (стін) через 28 днів після бетонування",
			unit = WorkUnit.PCS,
			minPrice = 900.0,
			maxPrice = 1500.0,
			averagePrice = 1147.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.UNDERFLOOR_HEATING,
			name = "Монтаж регуляторів теплих підлог",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 900.0,
			averagePrice = 665.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Інтернет і ТБ
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Прокладка кабелю для ТВ (без штроблення)",
			unit = WorkUnit.MPOG,
			minPrice = 20.0,
			maxPrice = 50.0,
			averagePrice = 35.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Прокладка кабелю для ТВ (зі штробленням)",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 180.0,
			averagePrice = 143.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Установка супутникової або ефірної антени",
			unit = WorkUnit.PCS,
			minPrice = 950.0,
			maxPrice = 1250.0,
			averagePrice = 1142.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Налаштування супутникової антени і тюнера",
			unit = WorkUnit.PCS,
			minPrice = 750.0,
			maxPrice = 1000.0,
			averagePrice = 950.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Установка розетки для ТБ або супутника",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 155.0,
			averagePrice = 137.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Установка розгалужувача або підсилювача ТВ",
			unit = WorkUnit.PCS,
			minPrice = 130.0,
			maxPrice = 200.0,
			averagePrice = 170.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Прокладка кабелю для інтернет (без штроблення)",
			unit = WorkUnit.MPOG,
			minPrice = 20.0,
			maxPrice = 50.0,
			averagePrice = 33.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Прокладка кабелю для інтернет (зі штробленням)",
			unit = WorkUnit.MPOG,
			minPrice = 120.0,
			maxPrice = 215.0,
			averagePrice = 153.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Установка розетки комп'ютерної",
			unit = WorkUnit.PCS,
			minPrice = 100.0,
			maxPrice = 200.0,
			averagePrice = 151.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Установка і настройка маршрутизатора",
			unit = WorkUnit.PCS,
			minPrice = 400.0,
			maxPrice = 550.0,
			averagePrice = 475.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.INTERNET_TV,
			name = "Обтиск крученої пари (роз'єм)",
			unit = WorkUnit.PCS,
			minPrice = 50.0,
			maxPrice = 140.0,
			averagePrice = 82.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Охоронні системи та контроль доступу
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Прокладка проводу для сигналізації (без штроблення)",
			unit = WorkUnit.MPOG,
			minPrice = 30.0,
			maxPrice = 40.0,
			averagePrice = 33.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Прокладка проводу для сигналізації (зі штробленням)",
			unit = WorkUnit.MPOG,
			minPrice = 50.0,
			maxPrice = 85.0,
			averagePrice = 73.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Установка проводового датчика, клавіатури, сирени",
			unit = WorkUnit.PCS,
			minPrice = 220.0,
			maxPrice = 300.0,
			averagePrice = 255.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Установка, підключення, налагодження 'централі'",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1000.0,
			averagePrice = 730.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Установка бездротового охоронного датчика",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 300.0,
			averagePrice = 240.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж датчика контролю положення дверей",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 260.0,
			averagePrice = 240.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж кнопки запиту на вихід",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 250.0,
			averagePrice = 213.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж накладного зчитувача магнітних карт",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 900.0,
			averagePrice = 700.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж і підключення мережевого контролера доступу",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1200.0,
			averagePrice = 967.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж замка накладного електромеханічного",
			unit = WorkUnit.PCS,
			minPrice = 600.0,
			maxPrice = 1200.0,
			averagePrice = 833.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж замка врізного електромеханічного",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 1500.0,
			averagePrice = 1167.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж електрозащілки двері (хвіртки)",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1200.0,
			averagePrice = 1000.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж хвіртки електромеханічної",
			unit = WorkUnit.PCS,
			minPrice = 1600.0,
			maxPrice = 1800.0,
			averagePrice = 1733.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж турнікета роторного поясного",
			unit = WorkUnit.PCS,
			minPrice = 3500.0,
			maxPrice = 5000.0,
			averagePrice = 4360.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж шлагбаума",
			unit = WorkUnit.PCS,
			minPrice = 3000.0,
			maxPrice = 5500.0,
			averagePrice = 4000.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж турнікета роторного повноростового",
			unit = WorkUnit.PCS,
			minPrice = 5500.0,
			maxPrice = 8000.0,
			averagePrice = 7175.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Пуско-налагоджувальні роботи системи доступу",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 2000.0,
			averagePrice = 1583.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Програмування системи контролю доступу",
			unit = WorkUnit.PCS,
			minPrice = 1300.0,
			maxPrice = 1500.0,
			averagePrice = 1433.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SECURITY_SYSTEMS,
			name = "Монтаж замка електромагнітного",
			unit = WorkUnit.PCS,
			minPrice = 650.0,
			maxPrice = 1200.0,
			averagePrice = 813.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Пожежна сигналізація
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж теплового пожежного сповіщувача",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 300.0,
			averagePrice = 257.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж димового пожежного сповіщувача",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 390.0,
			averagePrice = 308.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж пожежного сповіщувача полум'я",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 310.0,
			averagePrice = 260.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж ручного пожежного сповіщувача (ІПР)",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 380.0,
			averagePrice = 296.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж звукового сповіщувача в приміщенні",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 360.0,
			averagePrice = 295.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж звукового сповіщувача на вулиці",
			unit = WorkUnit.PCS,
			minPrice = 325.0,
			maxPrice = 440.0,
			averagePrice = 384.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж мовного пожежного сповіщувача",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 350.0,
			averagePrice = 317.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж світлового сповіщувача (табличка 'вихід')",
			unit = WorkUnit.PCS,
			minPrice = 180.0,
			maxPrice = 280.0,
			averagePrice = 243.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж вибухозахищеного пожежного сповіщувача",
			unit = WorkUnit.PCS,
			minPrice = 300.0,
			maxPrice = 380.0,
			averagePrice = 343.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж лінійного пожежного сповіщувача",
			unit = WorkUnit.PCS,
			minPrice = 260.0,
			maxPrice = 320.0,
			averagePrice = 293.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Монтаж блоку живлення пожежної сигналізації",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1000.0,
			averagePrice = 764.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Налаштування АСУ 1 категорії 2-4 каналу",
			unit = WorkUnit.PCS,
			minPrice = 2000.0,
			maxPrice = 3500.0,
			averagePrice = 2888.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Налаштування АСУ 1 категорії 8-16 каналів",
			unit = WorkUnit.PCS,
			minPrice = 3500.0,
			maxPrice = 5500.0,
			averagePrice = 4575.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Налаштування АСУ 1 категорії 16-24 каналів",
			unit = WorkUnit.PCS,
			minPrice = 5000.0,
			maxPrice = 8000.0,
			averagePrice = 6463.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Проектні роботи системи пожежної сигналізації",
			unit = WorkUnit.JOB,
			minPrice = 5000.0,
			maxPrice = 12000.0,
			averagePrice = 8750.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_ALARM,
			name = "Пуско-налагоджувальні роботи системи пожежної сигналізації",
			unit = WorkUnit.JOB,
			minPrice = 4750.0,
			maxPrice = 9000.0,
			averagePrice = 6550.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Системи відеоспостереження
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Монтаж внутрішньої відеокамери",
			unit = WorkUnit.PCS,
			minPrice = 380.0,
			maxPrice = 800.0,
			averagePrice = 666.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Установка вуличної відеокамери",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1000.0,
			averagePrice = 820.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Установка вуличної відеокамери на висоті",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 1350.0,
			averagePrice = 1010.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Установка і настройка відеореєстратора",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1200.0,
			averagePrice = 1000.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Встановлення та налаштування сервера відеоспостереження на базі ПК",
			unit = WorkUnit.PCS,
			minPrice = 1100.0,
			maxPrice = 1500.0,
			averagePrice = 1267.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Установка і настройка мікрофона",
			unit = WorkUnit.PCS,
			minPrice = 300.0,
			maxPrice = 600.0,
			averagePrice = 476.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Установка блоку живлення відеоспостереження в приміщенні",
			unit = WorkUnit.PCS,
			minPrice = 350.0,
			maxPrice = 750.0,
			averagePrice = 488.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Установка блоку живлення відеоспостереження на вулиці",
			unit = WorkUnit.PCS,
			minPrice = 600.0,
			maxPrice = 950.0,
			averagePrice = 738.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Налаштування доступу на відеореєстратор з комп'ютера/мобільного",
			unit = WorkUnit.PCS,
			minPrice = 300.0,
			maxPrice = 500.0,
			averagePrice = 400.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Налаштування модуля авторозпізнавання номерів",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 2000.0,
			averagePrice = 1667.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Налаштування модуля авторозпізнавання осіб",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 2000.0,
			averagePrice = 1683.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Монтаж відеопідсилювача-коректора / розгалужувача",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 700.0,
			averagePrice = 567.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VIDEO_SURVEILLANCE,
			name = "Підключення та монтаж охоронного відеомонітора",
			unit = WorkUnit.PCS,
			minPrice = 450.0,
			maxPrice = 980.0,
			averagePrice = 683.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Системи пожежогасіння
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Установка відкритого спринклера водяного пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 600.0,
			maxPrice = 1100.0,
			averagePrice = 900.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Установка прихованого спринклера водяного пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 800.0,
			maxPrice = 1300.0,
			averagePrice = 1100.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Монтаж манометра пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 700.0,
			maxPrice = 800.0,
			averagePrice = 750.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Монтаж насоса пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 2000.0,
			maxPrice = 3000.0,
			averagePrice = 2500.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Монтаж вузла управління пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 4000.0,
			maxPrice = 6000.0,
			averagePrice = 5333.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Монтаж бака системи пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 4000.0,
			maxPrice = 6000.0,
			averagePrice = 5000.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Пневматичне випробування системи пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 2500.0,
			maxPrice = 6000.0,
			averagePrice = 4500.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Гідравлічне випробування системи пожежогасіння",
			unit = WorkUnit.PCS,
			minPrice = 3500.0,
			maxPrice = 6000.0,
			averagePrice = 4833.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Монтаж системи спринклерного пожежогасіння під ключ",
			unit = WorkUnit.M2,
			minPrice = 2500.0,
			maxPrice = 6000.0,
			averagePrice = 3767.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Монтаж труби пожежогасіння (40-100мм)",
			unit = WorkUnit.MPOG,
			minPrice = 280.0,
			maxPrice = 330.0,
			averagePrice = 310.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.FIRE_EXTINGUISHING,
			name = "Монтаж труби пожежогасіння (150-250мм)",
			unit = WorkUnit.MPOG,
			minPrice = 500.0,
			maxPrice = 700.0,
			averagePrice = 630.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		//endregion
		//region Газифікація
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Газифікація приватного будинку під ключ",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Прокладка газової труби НД під землею",
			unit = WorkUnit.MPOG,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Монтаж газового лічильника",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Прокладка газової труби НД надземної",
			unit = WorkUnit.MPOG,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Супровід отримання тех. умов на газифікацію",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Виїзд на місце для консультації з газифікації (до 30 км)",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Врізка в газову трубу НД",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Врізка в газову трубу СД",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.GASIFICATION,
			name = "Перенесення газової труби в квартирі (під ключ)",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Ліфти та ескалатори
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELEVATORS,
			name = "Монтаж ліфта в приватному будинку",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELEVATORS,
			name = "Монтаж ліфта в багатоповерховому будинку",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELEVATORS,
			name = "Ремонт ліфта",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.ELEVATORS,
			name = "Діагностика несправностей ліфта",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Розумний будинок
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SMART_HOME,
			name = "Проектування (прорахунок) системи розумний будинок",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.SMART_HOME,
			name = "Монтаж системи 'розумний будинок'",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Альтернативні джерела енергії
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Проектування і розрахунок сонячної електростанції",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Проектування і розрахунок вітряної електростанції",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Проектування і розрахунок сонячного колектора гарячої води",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Проектування і розрахунок (геотермального) теплового насоса",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Монтаж і запуск сонячної електростанції",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Монтаж і запуск вітряної електростанції",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Монтаж і запуск сонячного колектора гарячої води",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.RENEWABLE_ENERGY,
			name = "Монтаж і запуск (геотермального) теплового насоса",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Системи вентиляції
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Монтаж повітроводів",
			unit = WorkUnit.MPOG,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Монтаж вентиляторів",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Монтаж фільтрів",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Монтаж зворотнього клапана",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Монтаж припливно-витяжної установки",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Монтаж канального вентилятора",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Монтаж дахового вентилятора",
			unit = WorkUnit.PCS,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Перевірка стану трубопроводів та запірної арматури",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Заправка водяного (гліколевого) контуру",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Злив водяного (гліколевого) контуру",
			unit = WorkUnit.JOB,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Утеплення повітроводів",
			unit = WorkUnit.MPOG,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.ENGINEERING,
			category = WorkCategory.VENTILATION,
			name = "Прокладання кабелю",
			unit = WorkUnit.MPOG,
			minPrice = 0.0,
			maxPrice = 0.0,
			averagePrice = 0.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		//endregion
		//endregion ІНЖЕНЕРНІ СИСТЕМИ ТА КОМУНІКАЦІЇ

		//region ЗНЕСЕННЯ І ДЕМОНТАЖ
		//region Демонтаж сантехніки
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_PLUMBING,
			name = "Демонтаж умивальника",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 400.0,
			averagePrice = 260.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_PLUMBING,
			name = "Демонтаж унітазу",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 356.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_PLUMBING,
			name = "Демонтаж ванни",
			unit = WorkUnit.PCS,
			minPrice = 350.0,
			maxPrice = 1000.0,
			averagePrice = 651.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_PLUMBING,
			name = "Демонтаж батареї",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 400.0,
			averagePrice = 305.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Демонтаж вікон і дверей
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WINDOWS_DOORS,
			name = "Демонтаж вікна",
			unit = WorkUnit.PCS,
			minPrice = 250.0,
			maxPrice = 520.0,
			averagePrice = 380.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WINDOWS_DOORS,
			name = "Демонтаж дверного блоку міжкімнатного",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 500.0,
			averagePrice = 366.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WINDOWS_DOORS,
			name = "Демонтаж броньованих дверей",
			unit = WorkUnit.PCS,
			minPrice = 500.0,
			maxPrice = 1000.0,
			averagePrice = 780.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WINDOWS_DOORS,
			name = "Демонтаж балконного блоку",
			unit = WorkUnit.PCS,
			minPrice = 1000.0,
			maxPrice = 2400.0,
			averagePrice = 1633.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WINDOWS_DOORS,
			name = "Демонтаж підвіконня, відливу",
			unit = WorkUnit.PCS,
			minPrice = 150.0,
			maxPrice = 380.0,
			averagePrice = 250.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WINDOWS_DOORS,
			name = "Демонтаж балконної тумби",
			unit = WorkUnit.PCS,
			minPrice = 1200.0,
			maxPrice = 2200.0,
			averagePrice = 1707.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Знесення стін і перегородок
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Демонтаж цегляної перегородки (пів цегли)",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 230.0,
			averagePrice = 168.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Демонтаж цегляної перегородки (1 цегла)",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 450.0,
			averagePrice = 318.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Знесення цегляної або гіпсової перегородки (1,5 цегли)",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 450.0,
			averagePrice = 358.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Знесення цегляної або гіпсової перегородки (2 цегли)",
			unit = WorkUnit.M2,
			minPrice = 250.0,
			maxPrice = 750.0,
			averagePrice = 494.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Демонтаж цегляної або гіпсової перегородки (2,5 цегли)",
			unit = WorkUnit.M2,
			minPrice = 295.0,
			maxPrice = 900.0,
			averagePrice = 542.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Знесення цегляної або гіпсової перегородки (3 цегли)",
			unit = WorkUnit.M2,
			minPrice = 400.0,
			maxPrice = 1000.0,
			averagePrice = 716.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Демонтаж стіни з ацеїду, гіпсоліту, ГКЛ",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 250.0,
			averagePrice = 176.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Демонтаж гіпсокартону",
			unit = WorkUnit.M2,
			minPrice = 77.0,
			maxPrice = 210.0,
			averagePrice = 153.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALLS_STRUCTURE,
			name = "Розшивання стиків з/б плит, тріщин",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 180.0,
			averagePrice = 133.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		//endregion
		//region Отвори і штроби
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.OPENINGS_STROBES,
			name = "Отвори в бетоні (до 10 см)",
			unit = WorkUnit.M2,
			minPrice = 750.0,
			maxPrice = 1500.0,
			averagePrice = 1206.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.OPENINGS_STROBES,
			name = "Отвори в бетоні (10-15 см)",
			unit = WorkUnit.M2,
			minPrice = 800.0,
			maxPrice = 2200.0,
			averagePrice = 1416.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.OPENINGS_STROBES,
			name = "Отвори в бетоні (15-20 см)",
			unit = WorkUnit.M2,
			minPrice = 1500.0,
			maxPrice = 2600.0,
			averagePrice = 2092.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.OPENINGS_STROBES,
			name = "Прорізання шва в цеглі (глибина до 5 см)",
			unit = WorkUnit.MPOG,
			minPrice = 80.0,
			maxPrice = 200.0,
			averagePrice = 137.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.OPENINGS_STROBES,
			name = "Прорізання вигнутого шва в цеглі (глибина до 5 см)",
			unit = WorkUnit.MPOG,
			minPrice = 110.0,
			maxPrice = 150.0,
			averagePrice = 134.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.OPENINGS_STROBES,
			name = "Розширення отвору для вхідних дверей",
			unit = WorkUnit.PCS,
			minPrice = 1110.0,
			maxPrice = 3000.0,
			averagePrice = 1801.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Видалення фарби
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення фарби зі стіни",
			unit = WorkUnit.M2,
			minPrice = 90.0,
			maxPrice = 200.0,
			averagePrice = 126.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення фарби зі стелі",
			unit = WorkUnit.M2,
			minPrice = 100.0,
			maxPrice = 180.0,
			averagePrice = 137.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення побілки зі стелі",
			unit = WorkUnit.M2,
			minPrice = 85.0,
			maxPrice = 150.0,
			averagePrice = 113.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення масляної фарби з труби",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 160.0,
			averagePrice = 124.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення масляної фарби з поверхні",
			unit = WorkUnit.M2,
			minPrice = 150.0,
			maxPrice = 260.0,
			averagePrice = 206.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення масляної фарби з підвіконня",
			unit = WorkUnit.MPOG,
			minPrice = 100.0,
			maxPrice = 180.0,
			averagePrice = 147.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення масляної фарби з рами вікна",
			unit = WorkUnit.M2,
			minPrice = 180.0,
			maxPrice = 200.0,
			averagePrice = 197.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення масляної фарби з радіатора",
			unit = WorkUnit.PCS,
			minPrice = 200.0,
			maxPrice = 330.0,
			averagePrice = 278.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.PAINT_REMOVAL,
			name = "Видалення масляної фарби з радіатора (за м²)",
			unit = WorkUnit.M2,
			minPrice = 200.0,
			maxPrice = 320.0,
			averagePrice = 272.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Демонтаж настінних покриттів
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALL_COVERINGS,
			name = "Демонтаж плитки",
			unit = WorkUnit.M2,
			minPrice = 95.0,
			maxPrice = 250.0,
			averagePrice = 165.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALL_COVERINGS,
			name = "Демонтаж штукатурки",
			unit = WorkUnit.M2,
			minPrice = 90.0,
			maxPrice = 200.0,
			averagePrice = 150.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALL_COVERINGS,
			name = "Зняття шпалер",
			unit = WorkUnit.M2,
			minPrice = 35.0,
			maxPrice = 100.0,
			averagePrice = 71.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALL_COVERINGS,
			name = "Демонтаж дерев'яної вагонки",
			unit = WorkUnit.M2,
			minPrice = 95.0,
			maxPrice = 150.0,
			averagePrice = 114.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_WALL_COVERINGS,
			name = "Демонтаж панелей",
			unit = WorkUnit.M2,
			minPrice = 80.0,
			maxPrice = 150.0,
			averagePrice = 121.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Демонтаж підлогових покриттів
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Демонтаж стяжки до 10 см",
			unit = WorkUnit.M2,
			minPrice = 120.0,
			maxPrice = 300.0,
			averagePrice = 222.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Видалення лінолеуму на клею",
			unit = WorkUnit.M2,
			minPrice = 60.0,
			maxPrice = 160.0,
			averagePrice = 112.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Демонтаж старого паркету",
			unit = WorkUnit.M2,
			minPrice = 75.0,
			maxPrice = 190.0,
			averagePrice = 128.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Демонтаж плінтуса",
			unit = WorkUnit.MPOG,
			minPrice = 20.0,
			maxPrice = 50.0,
			averagePrice = 36.0,
			targetSurface = TargetSurface.ANY_RUNNING_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Демонтаж стяжки до 5 см",
			unit = WorkUnit.M2,
			minPrice = 80.0,
			maxPrice = 220.0,
			averagePrice = 149.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Демонтаж лінолеуму або ковроліну",
			unit = WorkUnit.M2,
			minPrice = 40.0,
			maxPrice = 100.0,
			averagePrice = 69.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Демонтаж паркетної підлоги",
			unit = WorkUnit.M2,
			minPrice = 60.0,
			maxPrice = 132.0,
			averagePrice = 97.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_FLOOR_COVERINGS,
			name = "Демонтаж ламінату",
			unit = WorkUnit.M2,
			minPrice = 50.0,
			maxPrice = 132.0,
			averagePrice = 89.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//region Знесення будівель і будівель
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_BUILDINGS,
			name = "Демонтаж будівель і споруд (за м²)",
			unit = WorkUnit.M2,
			minPrice = 700.0,
			maxPrice = 1500.0,
			averagePrice = 1015.0,
			targetSurface = TargetSurface.NONE
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_BUILDINGS,
			name = "Демонтаж будівель і споруд (за м³)",
			unit = WorkUnit.M3,
			minPrice = 1000.0,
			maxPrice = 1900.0,
			averagePrice = 1378.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Демонтаж металоконструкцій
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_METAL,
			name = "Демонтаж металоконструкцій",
			unit = WorkUnit.TONN,
			minPrice = 2000.0,
			maxPrice = 4000.0,
			averagePrice = 2972.0,
			targetSurface = TargetSurface.NONE
		),
		//endregion
		//region Інший демонтаж
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_OTHER,
			name = "Демонтаж перекриття з дерева",
			unit = WorkUnit.M2,
			minPrice = 125.0,
			maxPrice = 300.0,
			averagePrice = 204.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		WorkService(
			section = WorkSection.DEMOLITION,
			category = WorkCategory.DEMO_OTHER,
			name = "Демонтаж перекриття з бетону",
			unit = WorkUnit.M2,
			minPrice = 700.0,
			maxPrice = 1500.0,
			averagePrice = 1010.0,
			targetSurface = TargetSurface.ANY_SQUARE_METER
		),
		//endregion
		//endregion ЗНЕСЕННЯ І ДЕМОНТАЖ
	)

	/**
	 * Отримати роботи для конкретної категорії
	*/
	fun getWorksForCategory(category: WorkCategory): List<WorkService> {
		return allWorks.filter { it.category == category }
	}
}