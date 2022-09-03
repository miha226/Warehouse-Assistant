package warehouse.assistant.domain.repository

import kotlinx.coroutines.flow.Flow
import warehouse.assistant.data.local.AuthorizedUserEntity
import warehouse.assistant.data.local.StorageCard
import warehouse.assistant.domain.model.*
import warehouse.assistant.util.Resource

interface StorageRepository {

    suspend fun getItems(query: String): Flow<Resource<List<Item>>>

    suspend fun insertItems(items:List<Item>)

    suspend fun getUsers(query: String):Flow<Resource<List<AuthorizedUser>>>

    //user functions
    suspend fun insertUser(user:AuthorizedUser)
    suspend fun updateUserRole(user: AuthorizedUser,role:String)

    suspend fun getUserByEmail(query: String):AuthorizedUser

    //storage entity functions
    suspend fun getStorages():Flow<Resource<List<Storage>>>
    suspend fun insertStorage(storage:Storage)
    suspend fun deleteStorage(storage: Storage)



    suspend fun createStorageCard(storageCards: List<StorageCardModel>,cardItems:List<StorageCardItem>)

    suspend fun getStorageCardsForStorage(storage: Storage,query: String):Flow<Resource<List<StorageCardItem>>>
    suspend fun getAllStorageCardsItems():Flow<Resource<List<StorageCardItem>>>

    suspend fun getAllStorageCards():Flow<Resource<List<StorageCard>>>

    suspend fun synchronizeLocalAndRemoteDB(onSynchronizedDone:()->Unit)
    suspend fun insertItemsFromFirebase(fetchComplete:()->Unit)

}