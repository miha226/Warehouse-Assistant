package warehouse.assistant.presentation.storage_card_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import warehouse.assistant.data.DateFormater
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.domain.model.StorageCardItem
import warehouse.assistant.domain.model.StorageCardModel
import warehouse.assistant.domain.repository.StorageRepository
import warehouse.assistant.util.Resource
import javax.inject.Inject

@HiltViewModel
class StorageCardViewModel @Inject constructor(
    val repository: StorageRepository
):ViewModel() {

    var state by mutableStateOf(StorageCardPageState())
    private var searchJob: Job? = null

    fun onEvent(event:StorageCardPageEvent){
        when(event){
            is StorageCardPageEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getItems()
                }
            }
            is StorageCardPageEvent.getItemByEAN -> {
                viewModelScope.launch {
                getItems(event.itemEAN)
                    delay(600L)
                if(state.items.isNotEmpty()){

                        state = state.copy(chosenItemForCardItem = state.items[0])
                        event.callback()
                    }
                }
            }
            is StorageCardPageEvent.chosenItemForStorageCard -> {
                state = state.copy(chosenItemForCardItem = event.item)
            }
            is StorageCardPageEvent.chosenItemTemp -> {
                state=state.copy(chosenItemTemp = event.item)
            }
            is StorageCardPageEvent.addStorageCardItemOnCard -> {
                var newStorageItem = StorageCardItem(time = 1, itemId = state.chosenItemForCardItem!!.itemId,
                quantityForSale = event.quantityForSale, forSaleInput = event.forSaleInput,
                quantityForService = event.quantityForService, forServiceInput = event.forServiceInput,
                price = state.chosenItemForCardItem!!.price)
                state = state.copy(
                    cardItems = state.cardItems+newStorageItem
                )
            }
            is StorageCardPageEvent.addStorageCardInDB -> {
                if(state.cardItems.isNotEmpty()){
                    saveStorageCard(event.storageName)
                }
            }

        }
    }

    private fun getItems(
        query: String = state.searchQuery.lowercase()
    ){
        viewModelScope.launch {
            repository.getItems(query=query)
                .collect{result->
                    when(result){
                        is Resource.Success -> {
                            result.data?.let { items ->
                                state = state.copy(
                                    items = items
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                    }
                }
        }
    }

    private fun saveStorageCard(storageName:String){
        viewModelScope.launch(Dispatchers.IO) {
            var username=FirebaseAuthImpl.getUser().username
            var time = DateFormater.getCurrentMillis()
            var storageCardItems = state.cardItems.map { it.copy(time = time) }
            var storageCards = storageCardItems.map {
                StorageCardModel(time = time, itemId = it.itemId, username = username, storageName = storageName) }
            repository.createStorageCard(storageCards,storageCardItems)
        }
    }


}