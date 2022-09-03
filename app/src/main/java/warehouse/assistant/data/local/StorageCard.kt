package warehouse.assistant.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["time","itemId","username","storageName"])
data class StorageCard(
    val time:Long=1,
    val itemId:String="0",
    val username:String="0",
    val storageName:String="0"
)
