package warehouse.assistant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [
        AuthorizedUserEntity::class,
        ItemEntity::class,
        StorageEntity::class,
        StorageCardItemEntity::class,
        StorageCard::class
    ],
    version = 4
)
abstract class CompanyDB: RoomDatabase() {
    abstract val userDao: AuthorizedUserDao
    abstract val dao: StorageDao
}