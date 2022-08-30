package warehouse.assistant.data.mapper

import warehouse.assistant.data.local.ItemEntity
import warehouse.assistant.domain.model.Item

fun ItemEntity.toItem(): Item{
    return Item(
        itemId = itemId,
        EAN = EAN,
        name = name,
        price = price
    )
}

fun Item.toItemEntity(): ItemEntity{
    return ItemEntity(
        itemId = itemId,
        EAN = EAN,
        name = name,
        price = price
    )
}