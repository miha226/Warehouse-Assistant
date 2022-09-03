package warehouse.assistant.presentation.users_page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScaffold(
    drawerState: DrawerState,
    content: @Composable() () -> Unit
){
    val scope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { warehouse.assistant.presentation.storages_page.StoragesTopAppBar(onNavigationIconClick = {
            scope.launch {
                drawerState.open()
            }
        })
        },
        content = {content()})
}