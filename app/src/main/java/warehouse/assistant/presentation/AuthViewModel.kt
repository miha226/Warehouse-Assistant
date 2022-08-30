package warehouse.assistant.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   val authService: FirebaseAuthImpl
): ViewModel() {
}