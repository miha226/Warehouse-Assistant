package warehouse.assistant.presentation.app_navigation

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import warehouse.assistant.domain.repository.StorageRepository
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val repository: StorageRepository
):ViewModel() {

    fun synchronizeLocalAndRemoteDB(onSynchronizedDone:()->Unit){
        viewModelScope.launch {
            repository.synchronizeLocalAndRemoteDB(){
                Log.d(ContentValues.TAG,"poziva li ovo ")
                onSynchronizedDone()
            }
        }
    }
}