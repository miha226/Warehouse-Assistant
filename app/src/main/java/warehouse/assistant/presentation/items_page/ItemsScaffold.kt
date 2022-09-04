package warehouse.assistant.presentation.app_navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScaffold(
 drawerState: DrawerState,
 onGetItemsFromFirebaseClick: () -> Unit,
 scanItem:()->Unit,
 content: @Composable() () -> Unit
){
    val scope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(),
    topBar = { ItemsTopAppBar(onNavigationIconClick = {
        scope.launch {
            drawerState.open()
        }
    }, onGetItemsFromFirebaseClick = {
        onGetItemsFromFirebaseClick()})
    }, floatingActionButton = { 
            QRCodeButton() {
                scanItem()
            }
        },
    content = {content()})
}