package warehouse.assistant.presentation.app_navigation

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import warehouse.assistant.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScaffold(
 drawerState: DrawerState,
 onGetItemsFromFirebaseClick: () -> Unit,
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
                Log.d(ContentValues.TAG,"Clicked on floating button 2")
            }
        },
    content = {content()})
}