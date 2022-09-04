package warehouse.assistant.presentation.login_page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.presentation.destinations.RegisterPageDestination
import warehouse.assistant.R
import warehouse.assistant.presentation.AuthViewModel
import warehouse.assistant.presentation.destinations.ItemsPageDestination
import warehouse.assistant.presentation.destinations.StoragesPageDestination

@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
@Composable
fun LoginPage(
    navigator: DestinationsNavigator,
    authViewModel : AuthViewModel = hiltViewModel()
){
    val authService = authViewModel.authService
    val mContext = LocalContext.current
    var password by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit ){
        if(authService.isUserLoggedIn()){
            authViewModel.setUser { }//LoggedUser.putUser(it) }
            navigator.navigate(StoragesPageDestination)
        }
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Card(modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp, min = 300.dp)) {

                OutlinedTextField(value =email, onValueChange = {email=it},
                label = {Text("Email")}, placeholder = { Text(text = "example@gmail.com") }
                    , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true,
                    maxLines = 1
                )
                OutlinedTextField(value =password, onValueChange = {password=it},
                label = { Text(text = "Password")}, singleLine = true, maxLines = 1,
                placeholder = { Text(text = "Password")}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) R.drawable.ic_visibility_black_24dp else
                            R.drawable.ic_visibility_off_black_24dp
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible =! passwordVisible }) {
                            Icon(painter = painterResource(id = image), contentDescription = description)
                        }
                    }
                )



                ElevatedButton(onClick = {
                    if(password.isNotEmpty() && email.isNotEmpty() && email.contains("@")){
                        if(!authService.isUserLoggedIn()){
                            authService.signIn(email,password,mContext){
                                if (it) {
                                    authViewModel.setUser {
                                        navigator.navigate(ItemsPageDestination)
                                    }
                                }
                            }
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onPrimary)) {
                    Text(text = "Login")
                }


                Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "If you don't have account:")
                    Button(onClick = { navigator.navigate(RegisterPageDestination) },
                    modifier = Modifier.padding(start = 20.dp)) {
                        Text(text = "Register page")
                    }
                }

            }
        }
    }
}