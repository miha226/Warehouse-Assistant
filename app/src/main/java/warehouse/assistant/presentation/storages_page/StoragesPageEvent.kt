package warehouse.assistant.presentation.storages_page

import warehouse.assistant.domain.model.Storage

sealed class StoragesPageEvent{
    data class createNewStorage(val nameOfStorage:String):StoragesPageEvent()
    data class deleteStorageFromDatabase(val storage:Storage):StoragesPageEvent()
}
