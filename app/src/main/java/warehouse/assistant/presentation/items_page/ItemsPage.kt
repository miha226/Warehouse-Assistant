package warehouse.assistant.presentation.items_page


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.presentation.QRCodeScanner.QRScanner
import warehouse.assistant.presentation.app_navigation.Drawer
import warehouse.assistant.presentation.app_navigation.ItemsScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ItemsPage(
    navigator: DestinationsNavigator,
    viewModel: ItemsPageViewModel = hiltViewModel()
){
    val state = viewModel.state
    var isQRScannerOpen by remember { mutableStateOf(false) }
    Drawer(navigateTo = {
        navigator.navigate(it)
    }) {
     ItemsScaffold(drawerState = it, scanItem = {
         isQRScannerOpen=true
     } ,onGetItemsFromFirebaseClick = {
         viewModel.getItemsFromFirebase()
     }) {
         if(isQRScannerOpen){
             QRScanner(returnCode = {
                 isQRScannerOpen=false
                 viewModel.onEvent(ItemsPageEvent.OnSearchQueryChange(it))
             })
         }else{
             Column(modifier = Modifier.fillMaxSize()) {
                 OutlinedTextField(value = state.searchQuery, onValueChange = {
                     viewModel.onEvent(ItemsPageEvent.OnSearchQueryChange(it))
                 }, modifier = Modifier
                     .padding(16.dp)
                     .fillMaxWidth(),
                     placeholder = {
                         Text(text = "Search...", maxLines = 1)
                     })
                 LazyColumn{
                     items(items = state.items,
                         key = {item -> item.itemId}){
                             item -> SingleItem(item = item)
                     }
                 }
             }
         }
     }
    }
}