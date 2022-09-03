package warehouse.assistant.presentation.storage_card_page

import warehouse.assistant.domain.model.Item
import warehouse.assistant.domain.model.StorageCardItem

data class StorageCardPageState(
    val cardItems:List<StorageCardItem> = emptyList(),
    val items: List<Item> = emptyList(),
    val searchQuery: String = "",
    val chosenItemForCardItem:Item? = null,
    val chosenItemTemp:Item?=null

)
