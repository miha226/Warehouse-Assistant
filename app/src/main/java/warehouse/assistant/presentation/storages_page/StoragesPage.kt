package warehouse.assistant.presentation.storages_page

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.presentation.app_navigation.Drawer
import warehouse.assistant.presentation.destinations.StockPageDestination

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Destination
fun StoragesPage(
    navigator: DestinationsNavigator,
    viewModel: StoragesViewModel = hiltViewModel()
){


    val state = viewModel.state
    var openDialogAddStorage by remember { mutableStateOf(false)}
    var openDialogDeleteStorage by remember { mutableStateOf(false)}
    var textFieldValue by remember{ mutableStateOf("")}
    var selectedStorageForDelete: Storage? = null
    var userRole = FirebaseAuthImpl.getUser().role

    fun onStorageClickNavigate(storage: Storage){
        Log.d(TAG,"Navigate to storage ${storage.storageName}")
        navigator.navigate(StockPageDestination(storage))
    }

    Drawer(navigateTo =  {
        navigator.navigate(it)
    }) {
       StoragesScaffold(drawerState = it) {
           LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
               items(items = state.storages,
                   key = {storage -> storage.storageName}){ storage ->
                   Card( modifier = Modifier
                       .run {
                           if (userRole == "owner" || userRole=="admin") {
                               Modifier.combinedClickable(
                                   onClick = {
                                       onStorageClickNavigate(storage)
                                       Log.d(
                                           TAG,
                                           "Navigate to storage normal click ${storage.storageName}"
                                       )
                                   },
                                   onLongClick = {
                                       selectedStorageForDelete = storage
                                       openDialogDeleteStorage = true
                                       Log.d(TAG, "This is long click ${storage.storageName}")
                                   }

                               )
                           } else {
                               Modifier.combinedClickable(
                                   onClick = {
                                       onStorageClickNavigate(storage)
                                       Log.d(TAG, "This is normal click for worker")
                                   }
                               )
                           }
                       }
                       .fillMaxWidth(0.8f)
                       .height(100.dp)
                       .padding(top = 16.dp)) {
                       Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                           Text(text = storage.storageName, fontWeight = FontWeight.Bold,
                               fontSize = 40.sp)
                       }

                   }
               }


               if (userRole=="admin" || userRole=="owner"){
                   item(key = "addStorageButton"){
                       Card(onClick = {
                           openDialogAddStorage =true}, modifier = Modifier.fillMaxWidth(0.6f)
                           .padding(top=20.dp).height(50.dp)) {
                           Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)) {
                               Text(text = "Add Storage", fontSize = 30.sp, color = MaterialTheme.colorScheme.onPrimary)
                           }

                       }
                   }
               }

           }

           if(openDialogAddStorage) {
               AlertDialog(onDismissRequest = { openDialogAddStorage = false },
                   title = { Text(text = "Add storage") },
                   confirmButton = {
                       Button(onClick = {
                           viewModel.onEvent(StoragesPageEvent.createNewStorage(textFieldValue))
                           openDialogAddStorage = false}) {
                       Text(text = "Add")
                   }},
                   dismissButton = {
                       Button(onClick = {
                            openDialogAddStorage = false
                           textFieldValue=""
                   }) {
                       Text(text = "Cancel")
                   }},
                   text = { OutlinedTextField(value = textFieldValue,
                       onValueChange = {text -> textFieldValue=text},
                   label = { Text(text = "exampleName")}) })
           }

           if (openDialogDeleteStorage){
               AlertDialog(onDismissRequest = { openDialogDeleteStorage = false },
               title = { Text(text = "Delete storage")},
               text = { Text(text = "Are you sure that you want to delete this storage")},
               confirmButton = { Button(onClick = {
                   viewModel.onEvent(StoragesPageEvent.deleteStorageFromDatabase(selectedStorageForDelete!!))
                   openDialogDeleteStorage=false
                   selectedStorageForDelete=null
               }) {
                   Text(text = "Delete")
               }},
               dismissButton = { Button(onClick = {
                   openDialogDeleteStorage=false
                   selectedStorageForDelete=null
               }) {
                   Text(text = "Cancel")
               }})
           }

       }
    }


}