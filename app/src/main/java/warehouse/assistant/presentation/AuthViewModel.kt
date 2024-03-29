package warehouse.assistant.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.domain.model.AuthorizedUser
import warehouse.assistant.domain.repository.StorageRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   val authService: FirebaseAuthImpl,
   val repository: StorageRepository
): ViewModel() {

   fun setUser(isDone:(AuthorizedUser)->Unit){
      viewModelScope.launch {
         var email = authService.getUserEmail()
         var info = async(Dispatchers.IO){repository.getUserByEmail(email)}
         if(info == null){addUserInLocalDB(email, username = "temp",1){}}
         else{
            FirebaseAuthImpl.putUser(info.await())
            isDone(info.await())
         }
      }
   }

   fun addUserInLocalDB(email:String,username:String,time:Long,isDone:()->Unit){
      var user = AuthorizedUser(email,username,"worker",time)
      viewModelScope.launch {
         repository.insertUser(user)
         isDone()
      }
   }

}