# Завдання з повної декомпозиції ViewModels

- `[/]` Етап 8: Спільна інфраструктура стану
    - `[ ]` Створити `ProjectStateRepository` у `domain/repository`
    - `[ ]` Додати `ProjectStateRepository` у `DatabaseModule` (або новий модуль)
- `[ ]` Етап 9: Перетворення моделей (Presentation)
    - `[ ]` Створити `RenovumAppViewModel`
    - `[ ]` Створити `RoomsScreenViewModel`
    - `[ ]` Створити `AddRoomScreenViewModel` та `EditRoomScreenViewModel`
    - `[ ]` Створити `CalcScreenViewModel`
    - `[ ]` Створити `WorksScreenViewModel`
    - `[ ]` Створити `DoneScreenViewModel`
    - `[ ]` Створити `WorkDialogViewModel`
    - `[ ]` Перейменувати `ArchiveViewModel` у `ArchiveScreenViewModel`
- `[ ]` Етап 10: Рефакторинг UI та Навігації
    - `[ ]` Очистити `NavGraph.kt` від параметрів ViewModel
    - `[ ]` Оновити кожен екран на використання власної ViewModel
    - `[ ]` Видалити застарілі `RoomViewModel` та `WorkViewModel`
- `[ ]` Етап 11: Перевірка та виправлення помилок
