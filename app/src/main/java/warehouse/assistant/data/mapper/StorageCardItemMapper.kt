package warehouse.assistant.data.mapper

import warehouse.assistant.data.local.StorageCardItemEntity
import warehouse.assistant.domain.model.StorageCardItem

fun StorageCardItemEntity.toStorageCardItem():StorageCardItem{
    return StorageCardItem(
        forSaleInput = forSaleInput,
        forServiceInput = forServiceInput,
        quantityForSale = quantityForSale,
        quantityForService = quantityForService,
        time = time,
        itemId = itemId,
        price = price
    )
}

fun StorageCardItem.toStorageCardItemEntity():StorageCardItemEntity{
    return StorageCardItemEntity(
        forSaleInput = forSaleInput,
        forServiceInput = forServiceInput,
        quantityForSale = quantityForSale,
        quantityForService = quantityForService,
        time = time,
        itemId = itemId,
        price = price
    )
}
