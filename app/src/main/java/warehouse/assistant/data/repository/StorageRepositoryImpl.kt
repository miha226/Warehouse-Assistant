package warehouse.assistant.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import warehouse.assistant.data.local.*
import warehouse.assistant.data.mapper.*
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.data.remote.dto.FirebaseStorage
import warehouse.assistant.domain.model.*
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

    //User functions
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

    override suspend fun getUserByEmail(query: String): AuthorizedUser {
        return userDao.getUSerByEmail(query).toAuthorizedUser()
    }

    override suspend fun insertUser(user: AuthorizedUser) {
        userDao.insertAuthorizedUser(user.toAuthorizedUserEntity())
    }

    override suspend fun updateUserRole(user: AuthorizedUser, role: String) {
        if(role=="worker" || role=="admin" || role=="fired" || role=="owner"){
            userDao.updateUserRole(user.email,role)
            firebaseStorage.updateUserRole(user,role){
                Log.d(TAG,"sucess of update $it")
            }
        }
    }


    //Storage entity functions
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

    override suspend fun deleteStorage(storage: Storage) {
        storageDao.deleteStorage(storage.toStorageEntity())
    }

    override suspend fun createStorageCard(storageCards: List<StorageCardModel>, cardItems: List<StorageCardItem>) {
        cardItems.forEach {
            storageDao.insertStorageCardItem(it.toStorageCardItemEntity())
        }
        storageCards.forEach {
            storageDao.insertStorageCard(it.toStorageCard())
        }
    }

    override suspend fun getStorageCardsForStorage(storage: Storage,query: String): Flow<Resource<List<StorageCardItem>>> {
        return flow {
            emit(Resource.Loading(true))
            val storageCards = storageDao.getAllStorageCardItemsForStorageByID(storage.storageName)

            emit(Resource.Success(
                data = storageCards.map { it.toStorageCardItem() }
            ))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun getAllStorageCardsItems(): Flow<Resource<List<StorageCardItem>>> {
        return flow {
            emit(Resource.Loading(true))
            val storageCards = storageDao.getAllStorageCardItems()

            emit(Resource.Success(
                data = storageCards.map { it.toStorageCardItem() }
            ))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun getAllStorageCards(): Flow<Resource<List<StorageCard>>> {
        return flow {
            emit(Resource.Loading(true))
            val storageCards = storageDao.getAllStorageCards()
            emit(Resource.Success(
                data = storageCards
            ))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun synchronizeLocalAndRemoteDB(onSynchronizedDone:()->Unit) {
        var storages:MutableList<Storage>?=null
            getStorages().collect{result->
            when(result){is Resource.Success -> {
                storages = result.data?.toMutableList()
                return@collect}}
        }
        var storageCardItemDB:MutableList<StorageCardItem>?=null
        getAllStorageCardsItems().collect{result->
            when(result){is Resource.Success ->{
                storageCardItemDB = result.data?.toMutableList()
                return@collect
            }}
        }
        var storageCardsDB:MutableList<StorageCard>?=null
        getAllStorageCards().collect{result->
            when(result){is Resource.Success ->{
                storageCardsDB = result.data?.toMutableList()
                return@collect
            }}
        }


        Log.d(TAG,"Usao u sync")
            firebaseStorage.getUsers().addOnSuccessListener { result ->
                Log.d(TAG,"Dohvatio usere")
                for (user in result){
                    CoroutineScope(Dispatchers.IO).async {
                        Log.d(TAG,"Usao u coroutinu")
                        Log.d(TAG,"Spremio usera u bazu ")
                        userDao.insertAuthorizedUser(user.toObject<AuthorizedUser>().toAuthorizedUserEntity())
                        Log.d(TAG,"prosao cijelu petlju ")
                    }
                }
            }.continueWith {
                firebaseStorage.getStorages().addOnSuccessListener { result ->
                    Log.d(TAG,"Dohvatio storage")
                    for(storage in result){
                        CoroutineScope(Dispatchers.IO).async {
                            var tempStorage=storage.toObject<Storage>()
                            if(storages?.contains(tempStorage) == true) storages!!.remove(tempStorage)
                            storageDao.insertStorage(tempStorage.toStorageEntity())
                            Log.d(TAG,"Prosao storage petlju")
                        }
                    }
                    storages?.toList()?.let { it1 -> firebaseStorage.updateStorages(storages= it1) }
                }.continueWith {
                    firebaseStorage.getStorageCardItemEntity().addOnSuccessListener { result ->
                        Log.d(TAG,"Dohvatio storage card item")
                        for(storageCardItem in result){
                            CoroutineScope(Dispatchers.IO).async {
                                var tempStorageCardItem=storageCardItem.toObject<StorageCardItem>()
                                if(storageCardItemDB?.contains(tempStorageCardItem) == true) storageCardItemDB!!.remove(tempStorageCardItem)
                                storageDao.insertStorageCardItem(tempStorageCardItem.toStorageCardItemEntity())
                                Log.d(TAG,"Prosao storage card item petlju")
                            }
                        }
                       storageCardItemDB?.toList()?.let { it -> firebaseStorage.updateStorageCardItems(storageCardItems = it) }
                    }.continueWith {
                        firebaseStorage.getStorageCards().addOnSuccessListener { result ->
                            Log.d(TAG,"Dohvatio storage card")
                            for(storageCard in result){
                                var tempStorageCard = storageCard.toObject<StorageCard>()
                                if (storageCardsDB?.contains(tempStorageCard)==true)storageCardsDB!!.remove(tempStorageCard)
                                CoroutineScope(Dispatchers.IO).launch {
                                    storageDao.insertStorageCard(tempStorageCard)
                                    Log.d(TAG,"Prosao storage card petlju")
                                }
                            }
                            storageCardsDB?.toList()?.let { it -> firebaseStorage.updateStorageCards(storageCards = it) }
                        }.continueWith {
                            CoroutineScope(Dispatchers.IO).async{
                                Log.d(TAG,"kraj u nastavak ")
                                FirebaseAuthImpl.putUser(getUserByEmail(FirebaseAuthImpl.getUser().email))
                                onSynchronizedDone()
                                Log.d(TAG,"kraj u nastavak2 ")
                            }
                        }
                    }
                }
            }
        }


}