package com.ulrezaj.renovum_1.data.model

import java.util.UUID

enum class WorkUnit(val displayName: String) {
	M2("м²"),
	M3("м³"),
	MPOG("м.пог."),
	PCS("шт."),
	POINT("точ."),
	TONN("тонна"),
	JOB("роб.");

	companion object {
		fun fromString(unit: String): WorkUnit {
			return when (unit.lowercase()) {
				"м2", "м²" -> M2
				"м3", "м³" -> M3
				"м.пог.", "мп" -> MPOG
				"шт.", "шт" -> PCS
				"точ.", "точка" -> POINT
				else -> JOB
			}
		}
	}
}

data class WorkService(
	val id: String = UUID.randomUUID().toString(),
	val section: WorkSection,
	val category: WorkCategory,
	val name: String,
	val unit: WorkUnit,
	val minPrice: Double,
	val maxPrice: Double,
	val averagePrice: Double,
	val targetSurface: TargetSurface = TargetSurface.NONE
)

data class AppliedWork(
	val id: String = UUID.randomUUID().toString(),
	val workId: String,
	val roomId: String,
	val quantity: Double,
	val priceAtTime: Double
)

enum class WorkSection(val displayName: String) {
	FINISHING("ОЗДОБЛЮВАЛЬНІ РОБОТИ"),
	ENGINEERING("ІНЖЕНЕРНІ СИСТЕМИ ТА КОМУНІКАЦІЇ"),
	DEMOLITION("ЗНЕСЕННЯ І ДЕМОНТАЖ")
}

enum class WorkCategory(val section: WorkSection, val displayName: String) {
	// --- ОЗДОБЛЮВАЛЬНІ РОБОТИ ---
	TILING(WorkSection.FINISHING, "Плиточні роботи"),
	PAINTING(WorkSection.FINISHING, "Малярні роботи"),
	PLASTERING(WorkSection.FINISHING, "Штукатурні роботи"),
	DRYWALL(WorkSection.FINISHING, "Монтаж гіпсокартону"),
	WALLPAPER(WorkSection.FINISHING, "Поклейка шпалер"),
	ROUGH_FLOORS(WorkSection.FINISHING, "Чорнові роботи по підлозі"),
	FLOORING(WorkSection.FINISHING, "Покриття для підлоги"),
	CEILING(WorkSection.FINISHING, "Стеля"),
	WAGONKA(WorkSection.FINISHING, "Монтаж вагонки"),
	WALL_DECOR(WorkSection.FINISHING, "Декоративне оздоблення стін"),
	WALL_PANELS(WorkSection.FINISHING, "Стінова панель"),
	STUCCO(WorkSection.FINISHING, "Ліпнина"),
	MURALS(WorkSection.FINISHING, "Розпис стін"),
	STONE_WORKS(WorkSection.FINISHING, "Роботи з каменем, мармуром, гранітом"),
	INDUSTRIAL_FLOORS(WorkSection.FINISHING, "Промислові підлоги"),

	// --- ІНЖЕНЕРНІ СИСТЕМИ ТА КОМУНІКАЦІЇ ---
	ELECTRIC(WorkSection.ENGINEERING, "Електромонтажні роботи"),
	PLUMBING(WorkSection.ENGINEERING, "Сантехнічні роботи"),
	HEATING_SYSTEMS(WorkSection.ENGINEERING, "Системи опалення"),
	CLIMATE_EQUIPMENT(WorkSection.ENGINEERING, "Кліматичне обладнання"),
	WATER_SUPPLY(WorkSection.ENGINEERING, "Водопостачання та очищення води"),
	UNDERFLOOR_HEATING(WorkSection.ENGINEERING, "Тепла підлога"),
	INTERNET_TV(WorkSection.ENGINEERING, "Інтернет і ТБ"),
	SECURITY_SYSTEMS(WorkSection.ENGINEERING, "Охоронні системи та контроль доступу"),
	FIRE_ALARM(WorkSection.ENGINEERING, "Пожежні сигналізації"),
	VIDEO_SURVEILLANCE(WorkSection.ENGINEERING, "Системи відеоспостереження"),
	FIRE_EXTINGUISHING(WorkSection.ENGINEERING, "Системи пожежогасіння"),
	GASIFICATION(WorkSection.ENGINEERING, "Газифікація"),
	ELEVATORS(WorkSection.ENGINEERING, "Ліфти та ескалатори"),
	SMART_HOME(WorkSection.ENGINEERING, "Розумний будинок"),
	RENEWABLE_ENERGY(WorkSection.ENGINEERING, "Альтернативні джерела енергії"),
	VENTILATION(WorkSection.ENGINEERING, "Системи вентиляції"),

	// --- ЗНЕСЕННЯ І ДЕМОНТАЖ ---
	DEMO_PLUMBING(WorkSection.DEMOLITION, "Демонтаж сантехніки"),
	DEMO_WINDOWS_DOORS(WorkSection.DEMOLITION, "Демонтаж вікон і дверей"),
	DEMO_WALLS_STRUCTURE(WorkSection.DEMOLITION, "Знесення стін і перегородок"),
	OPENINGS_STROBES(WorkSection.DEMOLITION, "Отвори і штроби"),
	PAINT_REMOVAL(WorkSection.DEMOLITION, "Видалення фарби"),
	DEMO_WALL_COVERINGS(WorkSection.DEMOLITION, "Демонтаж настінних покриттів"),
	DEMO_FLOOR_COVERINGS(WorkSection.DEMOLITION, "Демонтаж підлогових покриттів"),
	DEMO_BUILDINGS(WorkSection.DEMOLITION, "Знесення будівель і споруд"),
	DEMO_METAL(WorkSection.DEMOLITION, "Демонтаж металоконструкцій"),
	DEMO_OTHER(WorkSection.DEMOLITION, "Інший демонтаж")
}