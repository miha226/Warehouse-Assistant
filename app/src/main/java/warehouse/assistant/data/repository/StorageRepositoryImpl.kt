package warehouse.assistant.data.repository

import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import warehouse.assistant.data.local.*
import warehouse.assistant.data.mapper.*
import warehouse.assistant.data.remote.dto.FirebaseStorage
import warehouse.assistant.domain.model.AuthorizedUser
import warehouse.assistant.domain.model.Item
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.domain.model.StorageCardItem
import warehouse.assistant.domain.repository.StorageRepository
import warehouse.assistant.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepositoryImpl @Inject constructor(
    private val db: CompanyDB,
    private val firebaseStorage: FirebaseStorage
): StorageRepository {

    private val storageDao = db.dao
    private val userDao = db.userDao
    override suspend fun insertItems(items: List<Item>) {
        storageDao.insertItems(items.map { it.toItemEntity() })
    }

    override suspend fun insertItemsFromFirebase(fetchComplete:()->Unit){
        firebaseStorage.getItemsFromFirebaseAndConvertToItemsList {
            CoroutineScope(Dispatchers.IO).launch {
                storageDao.insertItems(it.map { it.toItemEntity() })
                fetchComplete()
            }
        }
    }

    override suspend fun getItems(
        query: String
    ): Flow<Resource<List<Item>>> {
        return flow {
            emit(Resource.Loading(true))
            val localItems = storageDao.getItemByIdOrEAN(query)
            emit(Resource.Success(
                data= localItems.map { it.toItem() }
            ))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun getUsers(query: String): Flow<Resource<List<AuthorizedUser>>> {
        return flow {
            emit(Resource.Loading(true))
            val users = userDao.getUsersByUsernameOrEmail(query)
            emit(Resource.Success(
                data = users.map { it.toAuthorizedUser() }
            ))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun getStorages(): Flow<Resource<List<Storage>>> {
        return flow {
            emit(Resource.Loading(true))
            val storages = storageDao.getAllStorages()
            emit(Resource.Success(
                data = storages.map { it.toStorage() }
            ))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun insertStorage(storage: Storage) {
        storageDao.insertStorage(storage.toStorageEntity())
    }

    override suspend fun createStorageCard(storage: Storage, cardItems: List<StorageCardItem>) {
        TODO("Not yet implemented")
    }

    override suspend fun getStorageCardsForStorage(storage: Storage,query: String): Flow<Resource<List<StorageCardItem>>> {
        return flow {
            emit(Resource.Loading(true))
            val storageCards = storageDao.getAllStorageCardItemsForStorageByID(storage.storageName,query)
            emit(Resource.Success(
                data = storageCards.storageCards.map { it.toStorageCardItem() }
            ))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun synchronizeLocalAndRemoteDB() {
            firebaseStorage.getUsers().addOnSuccessListener { result ->
                for (user in result){
                    CoroutineScope(Dispatchers.IO).launch {
                        userDao.insertAuthorizedUser(user.toObject<AuthorizedUserEntity>())
                    }
                }
            }.continueWith {
                firebaseStorage.getStorages().addOnSuccessListener { result ->
                    for(storage in result){
                        CoroutineScope(Dispatchers.IO).launch {
                            storageDao.insertStorage(storage.toObject<StorageEntity>())
                        }
                    }
                }
            }.continueWith {
                firebaseStorage.getStorageCardItemEntity().addOnSuccessListener { result ->
                    for(storageCardItem in result){
                        CoroutineScope(Dispatchers.IO).launch {
                            storageDao.insertStorageCardItem(storageCardItem.toObject<StorageCardItemEntity>())
                        }
                    }
                }
            }.continueWith {
                firebaseStorage.getStorageCards().addOnSuccessListener { result ->
                    for(storageCard in result){
                        CoroutineScope(Dispatchers.IO).launch {
                            storageDao.insertStorageCard(storageCard.toObject<StorageCard>())
                        }
                    }
                }
            }
        }


}