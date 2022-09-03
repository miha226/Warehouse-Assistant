package warehouse.assistant.presentation.stock_page

import warehouse.assistant.domain.model.Storage
import warehouse.assistant.domain.model.StorageCardItem
import warehouse.assistant.domain.model.StorageItem

data class StockPageState(
    val storage: Storage = Storage("null"),
    val storageCardItems:List<StorageCardItem> = emptyList(),
    val stockItems:List<StorageItem> = emptyList()
)
