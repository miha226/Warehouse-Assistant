package warehouse.assistant.presentation.storages_page

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import warehouse.assistant.presentation.app_navigation.ItemsTopAppBar
import warehouse.assistant.presentation.app_navigation.QRCodeButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoragesScaffold(
    drawerState: DrawerState,
    content: @Composable() () -> Unit
){
    val scope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { StoragesTopAppBar(onNavigationIconClick = {
            scope.launch {
                drawerState.open()
            }
        })},
        content = {content()})
}