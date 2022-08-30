package warehouse.assistant.presentation.items_page

import warehouse.assistant.domain.model.Item

data class ItemsPageState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val isGettingNewItemsFromFirebase: Boolean = false
)
