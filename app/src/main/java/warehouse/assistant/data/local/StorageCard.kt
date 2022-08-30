package warehouse.assistant.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["time","itemId","username","storageName"])
data class StorageCard(
    val time:Long,
    val itemId:String,
    val username:String,
    val storageName:String
)
