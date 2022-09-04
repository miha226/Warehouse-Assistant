package warehouse.assistant.presentation.storage_card_page

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import warehouse.assistant.presentation.app_navigation.QRCodeButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageCardScaffold(
    storageName:String,
    navigateBackToStorages:()->Unit,
    scanItem:()->Unit,
    content: @Composable() () -> Unit
){

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            QRCodeButton() {
                Log.d(TAG,"doslo do ovoga1")
                scanItem()
            }
        },
        topBar = { StorageCardPageTopAppBar(onNavigationIconClick = {
            navigateBackToStorages()
        }, storageName = storageName)
        },
        content = {content()})
}