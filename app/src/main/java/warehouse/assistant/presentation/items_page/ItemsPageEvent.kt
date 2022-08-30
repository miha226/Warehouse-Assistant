package warehouse.assistant.presentation.items_page

sealed class ItemsPageEvent{
    data class OnSearchQueryChange(val query: String): ItemsPageEvent()
    object GetNewItemsFromFirebase: ItemsPageEvent()
}
