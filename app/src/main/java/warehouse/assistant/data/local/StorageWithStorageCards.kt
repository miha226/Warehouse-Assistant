package warehouse.assistant.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class StorageWithStorageCards(
    @Embedded val storage: StorageEntity,
    @Relation(
        parentColumn = "storageName",
        entityColumn = "time",
        associateBy = Junction(StorageCard::class)
    ) val storageCards: List<StorageCardItemEntity>
)
