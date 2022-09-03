package warehouse.assistant.presentation.storage_card_page

import warehouse.assistant.domain.model.Item
import warehouse.assistant.domain.model.StorageCardItem
import warehouse.assistant.presentation.items_page.ItemsPageEvent

sealed class StorageCardPageEvent{
    data class addStorageCardItemOnCard(
        val quantityForSale:Int,
        val quantityForService:Int,
        val forSaleInput:Boolean,
        val forServiceInput:Boolean):StorageCardPageEvent()
    data class getItemByEAN(val itemEAN:String,val callback:()->Unit):StorageCardPageEvent()
    data class OnSearchQueryChange(val query: String): StorageCardPageEvent()
    data class chosenItemTemp(val item: Item?):StorageCardPageEvent()
    data class chosenItemForStorageCard(val item: Item?):StorageCardPageEvent()
    data class addStorageCardInDB(val storageName:String):StorageCardPageEvent()
}
