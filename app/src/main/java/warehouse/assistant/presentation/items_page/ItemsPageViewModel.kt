package warehouse.assistant.presentation.items_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import warehouse.assistant.data.remote.dto.FirebaseStorage
import warehouse.assistant.domain.repository.StorageRepository
import warehouse.assistant.util.Resource
import javax.inject.Inject

@HiltViewModel
class ItemsPageViewModel @Inject constructor(
    private val repository: StorageRepository,
    private val firebaseStorage: FirebaseStorage
): ViewModel() {

    var state by mutableStateOf(ItemsPageState())
    private var searchJob: Job? = null
    init {
        getItems("")
    }

    fun onEvent(event: ItemsPageEvent){
        when(event){
            is ItemsPageEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getItems()
                }
            }
            is ItemsPageEvent.GetNewItemsFromFirebase -> {

            }
        }
    }




    fun getItemsFromFirebase(){
        viewModelScope.launch {
            repository.insertItemsFromFirebase {
            getItems()
        }}
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
                        is Resource.Loading -> {
                            state = state.copy( isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}