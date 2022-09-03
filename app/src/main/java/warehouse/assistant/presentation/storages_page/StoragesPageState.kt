package warehouse.assistant.presentation.storages_page

import warehouse.assistant.domain.model.Storage

data class StoragesPageState(
    val storages:List<Storage> = emptyList()
)
