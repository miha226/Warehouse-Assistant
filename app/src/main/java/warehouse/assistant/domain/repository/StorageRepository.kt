package warehouse.assistant.domain.repository

import kotlinx.coroutines.flow.Flow
import warehouse.assistant.domain.model.AuthorizedUser
import warehouse.assistant.domain.model.Item
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.domain.model.StorageCardItem
import warehouse.assistant.util.Resource

interface StorageRepository {

    suspend fun getItems(query: String): Flow<Resource<List<Item>>>

    suspend fun insertItems(items:List<Item>)

    suspend fun getUsers(query: String):Flow<Resource<List<AuthorizedUser>>>

    suspend fun getStorages():Flow<Resource<List<Storage>>>

    suspend fun insertStorage(storage:Storage)

    suspend fun createStorageCard(storage: Storage,cardItems:List<StorageCardItem>)

    suspend fun getStorageCardsForStorage(storage: Storage,query: String):Flow<Resource<List<StorageCardItem>>>

    suspend fun synchronizeLocalAndRemoteDB()
    suspend fun insertItemsFromFirebase(fetchComplete:()->Unit)
}