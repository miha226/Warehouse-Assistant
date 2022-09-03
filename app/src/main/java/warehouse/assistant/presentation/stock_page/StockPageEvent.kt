package warehouse.assistant.presentation.stock_page

import warehouse.assistant.domain.model.Storage

sealed class StockPageEvent{
    object getStorageCards:StockPageEvent()
    object createNewStorageCard:StockPageEvent()
    object showStorageItems:StockPageEvent()
    object showStorageCards:StockPageEvent()
}
