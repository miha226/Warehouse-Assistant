package warehouse.assistant.presentation.stock_page

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.presentation.destinations.ItemsPageDestination
import warehouse.assistant.presentation.destinations.StorageCardPageDestination
import warehouse.assistant.presentation.destinations.StoragesPageDestination
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun StockPage(
    navigator: DestinationsNavigator,
    storage: Storage,
    viewModel:StockViewModel = hiltViewModel()
){
    var state = viewModel.state
    Log.d(TAG,"doslo je do pocetka $storage")

    LaunchedEffect(Unit ){
        viewModel.setStorage(storage)
    }
        



    StockScaffold( storageName = storage.storageName,
        navigateBackToStorages = {
        navigator.navigate(StoragesPageDestination)
    }
        , createNewStorageCardNavigation = {
            navigator.navigate(StorageCardPageDestination(storage))
        }) {
 
            Log.d(TAG,"prije itema ifa ${state.stockItems}")
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
                items(items = state.stockItems,
                key = {item -> item.itemID }){ item ->

                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp).padding(bottom = 20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.weight(0.55f), contentAlignment = Alignment.CenterStart) {
                                Text(text = item.itemID)
                            }
                            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                                    Text(text = "For sale")
                                    Text(text = item.quantityForSale.toString())
                                }
                            }
                            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                                    Text(text = "For service")
                                    Text(text = item.quantityForService.toString())
                                }
                            }
                        }
                    }
                }

            }
    }
}