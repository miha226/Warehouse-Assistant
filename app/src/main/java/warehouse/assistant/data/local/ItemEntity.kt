package warehouse.assistant.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity (
    @PrimaryKey(autoGenerate = false)
    val itemId: String,
    val EAN:String,
    val name:String,
    val price:Double)