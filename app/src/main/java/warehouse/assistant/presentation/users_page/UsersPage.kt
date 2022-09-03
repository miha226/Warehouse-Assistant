package warehouse.assistant.presentation.users_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.domain.model.AuthorizedUser
import warehouse.assistant.presentation.app_navigation.Drawer

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun UsersPage(
    navigator: DestinationsNavigator,
    viewmodel:UsersViewModel = hiltViewModel()
){

    val state = viewmodel.state
    var openDialogChangeRole by remember { mutableStateOf(false)}
    var openRoleDropdown by remember { mutableStateOf(false)}
    var selectedUserForRoleChange:AuthorizedUser? = null
    var disabledRoles:List<String> = state.disabledRoles
    var changedRole:String?=null
    Drawer(navigateTo = {
        navigator.navigate(it)
    }) {
        UsersScaffold(drawerState = it) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
                items(items = state.users,
                key = {user -> user.email}){ user ->
                    Card(onClick = {
                        if(FirebaseAuthImpl.getUser()!=user){
                            selectedUserForRoleChange = user
                            changedRole=user.role
                            openDialogChangeRole=true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(40.dp)
                        .padding(top = 16.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()) {
                            Text(text = user.email)
                            Text(text = user.username)
                            Text(text = user.role)
                        }
                    }
                }
            }

            if(openDialogChangeRole){
                AlertDialog(onDismissRequest = { openDialogChangeRole = false },
                confirmButton = { Button(onClick = {
                    if (selectedUserForRoleChange?.role != null && selectedUserForRoleChange?.role!=changedRole){
                        viewmodel.onEvent(UsersPageEvent.changeUsersRole(selectedUserForRoleChange!!,changedRole!!))
                        openDialogChangeRole=false
                    }
                }) {
                    Text(text = "Change role")
                }},
                dismissButton = { Button(onClick = {
                    openDialogChangeRole = false
                    selectedUserForRoleChange = null
                }) {
                    Text(text = "Cancel")
                }},
                title = { Text(text = "Change role")},
                    text = {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Role:")
                            Box(contentAlignment = Alignment.Center, modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .clickable {
                                    openRoleDropdown = true
                                }) {
                                Text(text = changedRole!!)
                                DropdownMenu(expanded = openRoleDropdown, onDismissRequest = { openRoleDropdown=false }) {
                                    state.roles.forEach { role->
                                        if (role!=changedRole && !state.disabledRoles.contains(role)){
                                            DropdownMenuItem(
                                                text = { Text(text = role) },
                                                onClick = { changedRole=role
                                                openRoleDropdown=false})
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }

    }
    
}