package warehouse.assistant.presentation.items_page

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.domain.model.Item
import warehouse.assistant.presentation.StorageViewModel
import warehouse.assistant.presentation.app_navigation.Drawer
import warehouse.assistant.presentation.app_navigation.ItemsScaffold
import warehouse.assistant.presentation.app_navigation.ItemsTopAppBar
import warehouse.assistant.presentation.destinations.StoragesPageDestination

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ItemsPage(
    navigator: DestinationsNavigator,
    viewModel: StorageViewModel = hiltViewModel()
){
    val state = viewModel.state

    Drawer(navigateTo = {
        navigator.navigate(it)
    }) {
     ItemsScaffold(drawerState = it, onGetItemsFromFirebaseClick = {
         Log.d(ContentValues.TAG,"Clicked on menu item proslo 0")
         viewModel.getItemsFromFirebase()
     }) {
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