package warehouse.assistant.presentation.stock_page


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import warehouse.assistant.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScaffold(
    storageName:String,
    navigateBackToStorages:()->Unit,
    createNewStorageCardNavigation:()->Unit,
    content: @Composable() () -> Unit
){

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
                               ElevatedButton(onClick = { createNewStorageCardNavigation() },
                                   modifier = Modifier
                                       .width(100.dp).height(100.dp)
                                       .padding(16.dp), shape = CircleShape, colors = ButtonDefaults.elevatedButtonColors(
                                       containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary
                                       )) {
                                   Icon(painter = painterResource(id = R.drawable.ic_writing_pad_clipboard_icon), contentDescription = "Create storage card")
                               }
        },
        topBar = { StockPageTopAppBar(onNavigationIconClick = {
            navigateBackToStorages()
        }, storageName = storageName)
        },
        content = {content()})
}