package warehouse.assistant.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StorageEntity(
    @PrimaryKey(autoGenerate = false)
    val storageName:String
)
