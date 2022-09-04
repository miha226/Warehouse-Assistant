package warehouse.assistant.presentation.stock_page


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.domain.model.StorageItem
import warehouse.assistant.domain.repository.StorageRepository
import warehouse.assistant.util.Resource
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    val repository: StorageRepository
):ViewModel() {

    var state by mutableStateOf(StockPageState())

    fun onEvent(event:StockPageEvent){
        when(event){
            is StockPageEvent.getStorageCards -> {
                getDataForStorage("")
            }

        }
    }

    fun setStorage(storage: Storage){
            state = state.copy(storage=storage)
            getDataForStorage("")
    }



    private fun getDataForStorage(query:String){
        viewModelScope.launch {
            repository.getStorageCardsForStorage(state.storage!!, query = query).collect{ result ->
                when(result){
                    is Resource.Success -> {
                        result.data?.let { cardItems ->
                            state = state.copy(storageCardItems = cardItems)
                        }
                        convertStorageCardItemsToStorageItem()
                    }
                    is Resource.Loading -> Unit
                    is Resource.Error -> Unit
                }
            }
        }
    }

    private fun convertStorageCardItemsToStorageItem(){
        if(state.storageCardItems.isNotEmpty()){
            val stockItems = state.storageCardItems.distinctBy { it.itemId }.map { card->
                StorageItem(card.itemId,
                    state.storageCardItems.filter {it.itemId.equals(card.itemId)}.sumOf { if (it.forSaleInput) it.quantityForSale else -it.quantityForSale } ,
                    state.storageCardItems.filter {it.itemId.equals(card.itemId)}.sumOf { if (it.forServiceInput) it.quantityForService else -it.quantityForService })
            }
            state=state.copy(stockItems = stockItems)
        }
    }


}