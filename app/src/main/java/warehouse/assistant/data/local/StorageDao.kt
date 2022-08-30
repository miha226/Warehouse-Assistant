package warehouse.assistant.data.local

import androidx.room.*

@Dao
interface StorageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorage(storage: StorageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorageCardItem(storageCardItemEntity: StorageCardItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorageCard(storageCard: StorageCard)

    @Query("DELETE FROM ItemEntity")
    suspend fun clearAllItems()

    //ITEM
    @Transaction
    @Query("SELECT * FROM ItemEntity WHERE LOWER(itemId) LIKE '%'||LOWER(:query)||'%' OR LOWER(:query) == EAN")
    suspend fun getItemByIdOrEAN(query:String):List<ItemEntity>

    //STORAGE
    @Transaction
    @Query("SELECT * FROM StorageEntity")
    suspend fun getAllStorages(): List<StorageEntity>


    //STORAGECARDITEM
    @Transaction
    @Query("SELECT * FROM StorageCardItemEntity ORDER BY time")
    suspend fun getStorageLog(): List<StorageCardItemEntity>

    @Transaction
    @Query("SELECT * FROM StorageCardItemEntity WHERE time == :time")
    suspend fun getStorageCardsByTime(time:String): List<StorageCardItemEntity>

    @Transaction
    @Query("SELECT * FROM StorageCardItemEntity WHERE itemId == :itemId")
    suspend fun getStorageCardsByItemId(itemId:String): List<StorageCardItemEntity>

    //STORAGE CARD FOR INDIVIDUAL STORAGE
    @Transaction
    @Query("SELECT * FROM StorageCard WHERE storageName == :storage AND itemId LIKE '%'||LOWER(:query)||'%'")
    suspend fun getAllStorageCardItemsForStorageByID(storage: String, query: String): StorageWithStorageCards


}