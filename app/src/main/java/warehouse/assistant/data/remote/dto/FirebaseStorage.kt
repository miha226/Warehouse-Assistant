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
import warehouse.assistant.data.local.StorageCardItemEntity
import warehouse.assistant.domain.model.AuthorizedUser
import warehouse.assistant.domain.model.Item
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


    /*suspend fun getItemsFromFirebaseAndConvertToItemsList():List<Item> = withContext(Dispatchers.IO){
        db.document(Constants.ITEMS).get().addOnSuccessListener { document ->
                if(document!=null){
                    //Log.d(TAG,"Document data ${document.data}")
                    itemsURL=document.data?.get("itemsURL").toString()
                    //Log.d(TAG,"URL data2 $itemsURL")
                }
            }.continueWith{
                var nesta = storage.getReferenceFromUrl(itemsURL).getStream().addOnSuccessListener { stream ->
                    CoroutineScope(Dispatchers.IO).launch{
                        var job = async{
                            itemsList = itemsParser.parse(stream.stream)
                            return@async itemsList
                        }
                    }
                }
            }
        delay(2000L)
        return@withContext itemsList
        }*/

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
            db.collection(Constants.USERS).document(user.email).update(role,role)
                .addOnSuccessListener { isSuccessfull(true) }
                .addOnFailureListener {  isSuccessfull(false)}
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