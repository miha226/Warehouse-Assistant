package warehouse.assistant.data.mapper

import warehouse.assistant.data.local.StorageEntity
import warehouse.assistant.domain.model.Storage

fun Storage.toStorageEntity():StorageEntity{
    return StorageEntity(
        storageName = storageName
    )
}
fun StorageEntity.toStorage():Storage{
    return Storage(
        storageName = storageName
    )
}