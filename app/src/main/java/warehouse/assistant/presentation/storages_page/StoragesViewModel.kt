package warehouse.assistant.presentation.storages_page

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.domain.repository.StorageRepository
import warehouse.assistant.util.Resource
import javax.inject.Inject

@HiltViewModel
class StoragesViewModel @Inject constructor(
    private val repository: StorageRepository
):ViewModel() {

    var state by mutableStateOf(StoragesPageState())
    init {
        getStorages()
    }

    fun onEvent(event: StoragesPageEvent){
        when(event){
            is StoragesPageEvent.createNewStorage -> {
                createStorage(event.nameOfStorage)
            }
            is StoragesPageEvent.deleteStorageFromDatabase -> {
                deleteStorage(event.storage)
            }
        }
    }

    private fun deleteStorage(storage: Storage) {
        viewModelScope.launch {
            repository.deleteStorage(storage)
            getStorages()
        }
    }

    private fun getStorages(){
        viewModelScope.launch{
            repository.getStorages().collect{ result ->
                when(result){
                    is Resource.Error -> {
                        Log.d(TAG,"Error in getStorages")
                    }
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        Log.d(TAG,"success in getStorages")
                        result.data?.let { storages ->
                            state = state.copy( storages=storages)
                        }
                        Log.d(TAG,"success in getStorages2 ${state.storages}")
                    }
                }
            }
        }
    }

    private fun createStorage(nameOfStorage:String){
        viewModelScope.launch{
            var storage = Storage(storageName = nameOfStorage)
            repository.insertStorage(storage)
            getStorages()
        }
    }



}