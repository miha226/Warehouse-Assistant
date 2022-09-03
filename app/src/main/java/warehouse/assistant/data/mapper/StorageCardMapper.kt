package warehouse.assistant.data.mapper

import warehouse.assistant.data.local.StorageCard
import warehouse.assistant.domain.model.StorageCardModel

fun StorageCard.toStorageCardModel():StorageCardModel{
    return StorageCardModel(
        time=time,
        itemId=itemId,
        username=username,
        storageName=storageName
    )
}

fun StorageCardModel.toStorageCard():StorageCard{
    return StorageCard(
        time=time,
        itemId=itemId,
        username=username,
        storageName=storageName
    )
}