package warehouse.assistant.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["time","itemId"])
data class StorageCardItemEntity(
    @ColumnInfo(name = "time")
    val time: Long=1,
    @ColumnInfo(name = "itemId")
    val itemId: String="null",
    @ColumnInfo(name = "forSaleInput")
    val forSaleInput: Boolean = true,
    @ColumnInfo(name = "forServiceInput")
    val forServiceInput: Boolean = true,
    @ColumnInfo(name = "quantityForSale")
    val quantityForSale: Int = 0,
    @ColumnInfo(name = "quantityForService")
    val quantityForService: Int = 0,
    @ColumnInfo(name = "price")
    val price:Double = 0.0
)
