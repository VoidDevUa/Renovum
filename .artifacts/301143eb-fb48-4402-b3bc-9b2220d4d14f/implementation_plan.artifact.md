# План рефакторингу проєкту Renovum

Цей план розбиває рефакторинг на логічні етапи (коміти) для поступового покращення архітектури, розвантаження ViewModel та оптимізації коду, зберігаючи при цьому унікальні фішки додатка (інтерфейс для лівші та українську локалізацію).

## User Review Required

> [!IMPORTANT]
> **Впровадження Dependency Injection**: Для чистого розділення ViewModel я пропоную використовувати **Hilt**. Це промисловий стандарт, який дозволить автоматично прокидати репозиторії та UseCases, уникаючи "макаронних" фабрик у `RenovumApp`.
>
> **Зміна структури ViewModel**: Ми розділимо один гігантський `RoomViewModel` на 4 спеціалізовані моделі. Це потребуватиме оновлення `NavGraph`.

## Proposed Changes

### Етап 0: Впровадження Hilt (Infrastructure)
Створення фундаменту для Dependency Injection.

#### [MODIFY] [libs.versions.toml](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/gradle/libs.versions.toml)
- Додавання версій та бібліотек для Hilt (dagger-hilt-android, hilt-navigation-compose).

#### [MODIFY] [build.gradle.kts](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/build.gradle.kts) та [app/build.gradle.kts](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/build.gradle.kts)
- Підключення плагінів Hilt.

#### [NEW] [RenovumApplication.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/RenovumApplication.kt)
- Створення класу Application з анотацією `@HiltAndroidApp`.

#### [MODIFY] [MainActivity.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/MainActivity.kt)
- Додавання анотації `@AndroidEntryPoint`.

---

### Етап 1: Domain Layer та Розрахунки
Відокремлення математичної логіки від UI.

#### [NEW] [RoomCalculationsUseCase.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/domain/usecase/RoomCalculationsUseCase.kt)
- Перенесення логіки `calculateRoomData`, `getSurfaceValue` та `getAvailableOptions` сюди.
- Чисті функції для тестування.

#### [MODIFY] [RoomViewModel.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/viewmodel/RoomViewModel.kt)
- Видалення методів розрахунку, виклик через UseCase.

---

### Етап 2: Оптимізація Data Layer
Усунення блокуючих операцій при запуску.

#### [MODIFY] [WorkDataRepository.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/data/repositories/WorkDataRepository.kt)
- Зміна `init` на асинхронне завантаження.
- Використання `CoroutineScope` для парсингу JSON.

---

### Етап 3: Ресурси та Локалізація
Підготовка до чистого коду без хардкоду.

#### [MODIFY] [strings.xml](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/res/values/strings.xml)
- Винесення всіх українських рядків з ViewModel та Screens у ресурси.

---

### Етап 4: Розподіл ViewModel (Decomposition)
Найважливіший етап для чистоти коду.

#### [NEW] [ArchiveViewModel.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/viewmodel/ArchiveViewModel.kt)
- Управління файлами DOCX, видалення, вибір.

#### [NEW] [WorkViewModel.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/viewmodel/WorkViewModel.kt)
- Робота з `AppliedWork`, категорії, знижки.

#### [NEW] [ReportViewModel.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/viewmodel/ReportViewModel.kt)
- Логіка експорту (виклик `WordExportManager`).

---

### Етап 5: Очищення UI та Навігації
Покращення головного екрана та TopAppBar.

#### [MODIFY] [RenovumApp.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/RenovumApp.kt)
- Зменшення розміру файлу за рахунок винесення логіки TopAppBar у конфігурації.
- Оптимізація перемикання LayoutDirection (чистіша реалізація фішки для лівші).

## Verification Plan

### Automated Tests
- Юніт-тести для `RoomCalculationsUseCase` (перевірка площ для різних форм кімнат).
- Перевірка ініціалізації бази даних через Hilt.

### Manual Verification
- Перевірка роботи додатка в обох режимах (правша/лівша).
- Генерація тестового звіту Word.
- Перевірка швидкості запуску після переведення JSON завантаження у фон.
