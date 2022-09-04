package warehouse.assistant.presentation.users_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.domain.model.AuthorizedUser
import warehouse.assistant.domain.repository.StorageRepository
import warehouse.assistant.util.Resource
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: StorageRepository
):ViewModel() {

    var state by mutableStateOf(UsersPageState())
    init {
        setDisabledRoles()
        getUsers()
    }

    fun onEvent(event: UsersPageEvent){
        when(event){
            is UsersPageEvent.changeUsersRole -> {
                changeUsersRole(event.user,event.role)
            }
        }
    }

    private fun getUsers(){
        viewModelScope.launch {
            repository.getUsers("").collect{ result ->
                when(result){
                    is Resource.Success -> {
                        result.data?.let { users ->
                            state = state.copy(users = users)
                        }
                    }
                    is Resource.Loading ->  Unit
                    is Resource.Error -> Unit
                }
            }
        }
    }

    private fun setDisabledRoles(){
        var user = FirebaseAuthImpl.getUser()
        if (user.role=="admin"){
            state=state.copy(disabledRoles = listOf("owner"))
        }
    }
    private fun changeUsersRole(user:AuthorizedUser,role:String){
        viewModelScope.launch {
            repository.updateUserRole(user,role)
            getUsers()
        }
    }
}