package warehouse.assistant.data.remote.dto


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import warehouse.assistant.data.Constants
import warehouse.assistant.data.DateFormater
import warehouse.assistant.data.csv.ItemsParser
import warehouse.assistant.data.local.ItemEntity
import warehouse.assistant.data.local.StorageCard
import warehouse.assistant.data.local.StorageCardItemEntity
import warehouse.assistant.domain.model.AuthorizedUser
import warehouse.assistant.domain.model.Item
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.domain.model.StorageCardItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseStorage @Inject constructor(
    private val itemsParser: ItemsParser
) {
    private val storage = Firebase.storage
    val db = Firebase.firestore
    lateinit var itemsURL: String


    var itemsList:List<Item> = emptyList()


    suspend fun getItemsFromFirebaseAndConvertToItemsList(itemsList:(List<Item>)->Unit) {
        db.document(Constants.ITEMS).get().addOnSuccessListener { document ->
            if(document!=null){
                itemsURL=document.data?.get("itemsURL").toString()
            }
        }.continueWith{
            storage.getReferenceFromUrl(itemsURL).getStream().addOnSuccessListener { stream ->
                CoroutineScope(Dispatchers.IO).launch{
                    var items = async{
                        return@async itemsParser.parse(stream.stream)
                    }
                    itemsList(items.await())
                }
            }
        }
    }


    fun updateUserRole(user:AuthorizedUser,role:String,isSuccessfull:(Boolean)->Unit){
            db.collection(Constants.USERS).document(user.email).update("role",role)
                .addOnSuccessListener { isSuccessfull(true) }
                .addOnFailureListener {  isSuccessfull(false)}
    }

    fun updateStorages(storages:List<Storage>){
        var ref = db.collection(Constants.STORAGE_ENTITY)
        db.runBatch { batch ->
            storages.forEach { storage ->

                batch.set(ref.document(storage.storageName),storage)
            }
        }
    }

    fun updateStorageCardItems(storageCardItems:List<StorageCardItem>){
        var ref = db.collection(Constants.STORAGE_CARD_ITEM_ENTITY)
        db.runBatch { batch ->
            storageCardItems.forEach { cardItem ->

                batch.set(ref.document(cardItem.itemId+" "+cardItem.time.toString()),cardItem)
            }
        }
    }

    fun updateStorageCards(storageCards:List<StorageCard>){
        var ref = db.collection(Constants.STORAGE_CARD)
        db.runBatch { batch ->
            storageCards.forEach { card ->
                batch.set(ref.document(card.itemId+" "+card.time.toString()),card)
            }
        }
    }



    fun getStorageCardItemEntity(): Task<QuerySnapshot> {
        return db.collection(Constants.STORAGE_CARD_ITEM_ENTITY).get()
    }
    fun getStorages():Task<QuerySnapshot>{
        return  db.collection(Constants.STORAGE_ENTITY).get()
    }
    fun getStorageCards():Task<QuerySnapshot>{
        return  db.collection(Constants.STORAGE_CARD).get()
    }
    fun getUsers():Task<QuerySnapshot>{
        return  db.collection(Constants.USERS).get()
    }

}