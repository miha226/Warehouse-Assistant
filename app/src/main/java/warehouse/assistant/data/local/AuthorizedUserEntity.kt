package warehouse.assistant.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthorizedUserEntity (
    @PrimaryKey(autoGenerate = false)
    val email: String,
    val username: String,
    val role: String,
    val time: Long
)