# План реструктуризації пакетів Renovum

Цей план спрямований на групування існуючих пакетів для досягнення ієрархічної структури Clean Architecture. Це зменшить кількість папок у корені та чітко розмежує шари Presentation, Domain та Data.

## User Review Required

> [!IMPORTANT]
> **Зміна пакетів (Package Renaming)**: Переміщення папок призведе до зміни `package` у багатьох файлах. Це автоматично оновить імпорти, але я проведу фінальну перевірку через `gradle build`, щоб переконатися, що Hilt та Compose працюють коректно.

## Proposed Changes

### Presentation Layer
Групування всього, що стосується інтерфейсу та взаємодії з користувачем.

#### [MOVE] `ui/` -> `presentation/ui/`
#### [MOVE] `viewmodel/` -> `presentation/viewmodel/`
#### [MOVE] `navigation/` -> `presentation/navigation/`

### Domain Layer
Концентрація бізнес-логіки та описів сутностей.

#### [MOVE] `model/` -> `domain/model/`

---

### Infrastructure & Core
Очищення кореня від допоміжних та технічних пакетів.

#### [NEW] Пакет `core/`
- **[MOVE]** `di/` -> `core/di/`
- **[MOVE]** `utility/Logger.kt` -> `core/util/Logger.kt`
- **[MOVE]** `utility/RenovumFileProvider.kt` -> `core/util/RenovumFileProvider.kt`

#### [NEW] Пакет `data/remote/`
- **[MOVE]** `utility/WordExportManager.kt` -> `data/remote/WordExportManager.kt`
- **[MOVE]** `utility/RenovumNotificationManager.kt` -> `data/remote/RenovumNotificationManager.kt`

---

### Коренева структура після всіх змін:
- `data/`
  - `local/`
  - `remote/` (Word, Notifications)
  - `repositories/`
- `domain/`
  - `model/`
  - `usecase/`
- `presentation/`
  - `ui/`
  - `viewmodel/`
  - `navigation/`
- `core/`
  - `di/`
  - `util/`
- `MainActivity.kt`, `RenovumApp.kt`, `RenovumApplication.kt`

## Verification Plan

### Automated Tests
- `app:assembleDebug` — повна перевірка компіляції після зміни пакетів.

### Manual Verification
- Перевірка запуску додатка (Hilt ініціалізація).
- Перевірка навігації між екранами.
