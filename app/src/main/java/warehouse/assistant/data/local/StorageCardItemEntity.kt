package warehouse.assistant.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["time","itemId"])
data class StorageCardItemEntity(
    val time: Long,
    val itemId: String,
    val forSaleInput: Boolean = true,
    val forServiceInput: Boolean = true,
    val quantityForSale: Int = 0,
    val quantityForService: Int = 0
)
