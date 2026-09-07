# План повної декомпозиції (Atomic ViewModels)

Цей план описує перехід від кількох великих ViewModels до атомарних моделей, де кожен екран та складний діалог має власну ViewModel. Спільний стан буде синхронізуватися через `ProjectStateRepository`.

## User Review Required

> [!IMPORTANT]
> **Naming Convention**: Всі нові ViewModels будуть називатися відповідно до їхніх елементів: `RoomsScreenViewModel`, `WorkDialogViewModel` тощо.
>
> **Single Source of Truth**: Ми впроваджуємо `ProjectStateRepository` як центральне сховище для стану, що має бути спільним (вибрана кімната, відсоток знижки).

## Proposed Changes

### 1. Domain Layer: Спільний стан
#### [NEW] [ProjectStateRepository.kt](file:///C:/THTY/4_kurs/Bakalavrska/Renovum_1/app/src/main/java/com/void_dev_ua/renovum/domain/repository/ProjectStateRepository.kt)
- Буде містити `selectedRoomId: StateFlow<String?>`.
- Буде містити `globalDiscount: StateFlow<Double>`.

### 2. Presentation Layer: Нові ViewModels

#### Головний екран та Загальне
- **[NEW] `RenovumAppViewModel`**: Для стану в тулбарі та дрейвері (вибір кімнати в тулбарі впливає на глобальний стан).
- **[NEW] `RoomsScreenViewModel`**: Тільки список кімнат та видалення.

#### Робота з кімнатами
- **[NEW] `AddRoomScreenViewModel`**: Стан вводу даних при створенні кімнати.
- **[NEW] `EditRoomScreenViewModel`**: Стан редагування існуючої кімнати.
- **[NEW] `CalcScreenViewModel`**: Результати розрахунків та список прорізів.

#### Роботи та Послуги
- **[NEW] `WorksScreenViewModel`**: Каталог послуг, категорії, фільтрація.
- **[NEW] `DoneScreenViewModel`**: Підсумки, групування робіт.
- **[NEW] `WorkDialogViewModel`**: Складний стан діалогу додавання/редагування роботи (пейджер, ввід).

#### Архів та Звіти
- **[RENAME] `ArchiveViewModel`** -> `ArchiveScreenViewModel`.
- **[RENAME] `ReportViewModel`** -> `ReportServiceViewModel` (або аналогічно, оскільки це сервісна логіка).

### 3. UI Layer: Автономність
- Кожен Composable екран тепер буде викликати `hiltViewModel()` всередині себе.
- `NavGraph` та `RenovumApp` перестануть бути "посередниками" для передачі об'єктів ViewModel.

## Verification Plan

### Automated Tests
- Перевірка збірки через `gradle build`.
- Перевірка реактивності: зміна кімнати в `RenovumAppViewModel` (через тулбар) має миттєво оновити дані в `CalcScreenViewModel`.

### Manual Verification
- Пройти повний шлях користувача: Створення кімнати -> Розрахунок -> Додавання робіт через діалог -> Перевірка підсумків -> Експорт.
- Перевірка режиму лівші з новими моделями.
