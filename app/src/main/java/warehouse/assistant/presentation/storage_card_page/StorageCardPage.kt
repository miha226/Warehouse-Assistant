package warehouse.assistant.presentation.storage_card_page


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.domain.model.Storage
import warehouse.assistant.presentation.QRCodeScanner.QRScanner
import warehouse.assistant.presentation.items_page.SingleItem

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun StorageCardPage(
    navigator: DestinationsNavigator,
    storage: Storage,
    viewModel:StorageCardViewModel= hiltViewModel()
){
    var state=viewModel.state
    var isQRScannerOpen by remember { mutableStateOf(false)}
    var editDialog by remember { mutableStateOf(false)}
    var manualAddDialog by remember { mutableStateOf(false) }
    var itemsDropDownMenu by remember { mutableStateOf(false)}
    var code = ""
    var newItemQuantityForSale by remember { mutableStateOf("0")}
    var newItemQuantityForService by remember { mutableStateOf("0")}
    var newItemForSaleInput by remember { mutableStateOf(true)}
    var newItemForServiceInput by remember { mutableStateOf(true)}
    StorageCardScaffold(
        storageName = storage.storageName,
        navigateBackToStorages = { navigator.navigateUp() },
        scanItem = {
            isQRScannerOpen=!isQRScannerOpen }) {
        if(isQRScannerOpen){
            QRScanner( returnCode = {
                isQRScannerOpen=false
                viewModel.onEvent(StorageCardPageEvent.getItemByEAN(it, callback = {
                    editDialog=true
                }))

            })

        }else{
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = FirebaseAuthImpl.getUser().username)
                    Text(text = FirebaseAuthImpl.getUser().role)
                }
                LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
                    items(items = state.cardItems,
                        key = {item -> item.itemId}){
                            item -> StorageCardSingleItem(storageCardItem = item)
                    }

                    item(key = "manual add"){
                        Button(onClick = { manualAddDialog=true }) {
                            Text(text = "Add item manually")
                        }
                    }

                    item(key="complete storage card"){
                        if(!state.cardItems.isEmpty()){
                            Button(onClick = { viewModel.onEvent(StorageCardPageEvent.addStorageCardInDB(storageName = storage.storageName))
                                             navigator.navigateUp()},
                                modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(50.dp)) {
                                Text(text = "Save changes")
                            }
                        }
                    }
                }

            }
        }
        fun clearValues(){
            newItemForSaleInput = true
            newItemForServiceInput = true
            newItemQuantityForSale = "0"
            newItemQuantityForService = "0"
        }
        
        if(editDialog){
            AlertDialog(onDismissRequest = { editDialog=false
                clearValues()
                viewModel.onEvent(StorageCardPageEvent.chosenItemForStorageCard(null))},
            confirmButton = { Button(onClick = {
                if(newItemQuantityForSale.toInt()!=0 || newItemQuantityForService.toInt()!=0){
                        viewModel.onEvent(StorageCardPageEvent.addStorageCardItemOnCard(
                            quantityForSale = newItemQuantityForSale.toInt(),
                            quantityForService = newItemQuantityForService.toInt(),
                            forServiceInput = newItemForServiceInput,
                            forSaleInput = newItemForSaleInput
                        ))
                    clearValues()
                    viewModel.onEvent(StorageCardPageEvent.chosenItemForStorageCard(null))
                    editDialog=false
                }
            }) {
                Text(text = "Add")
            }},
            dismissButton = { Button(onClick = { editDialog=false
                clearValues()
                viewModel.onEvent(StorageCardPageEvent.chosenItemForStorageCard(null))}) {
                Text(text = "Cancel")
            }},
            title = { Text(text = "Add item to storage card")},
            text = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                    if(state.chosenItemForCardItem!=null){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(value = newItemQuantityForSale, label = { Text(text = "Quantity fo sale")},
                                maxLines = 1, singleLine = true, modifier = Modifier.weight(1f),
                                onValueChange = {value ->  if(value!=null){
                                    newItemQuantityForSale = value.filter { it.isDigit() }
                                }else newItemQuantityForSale="0"   },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f)) {
                                Text(text = "Is this input:")
                                Checkbox(checked = newItemForSaleInput, onCheckedChange = {newItemForSaleInput=!newItemForSaleInput})
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(value = newItemQuantityForService, label = { Text(text = "Quantity fo service")},
                                maxLines = 1, singleLine = true, modifier = Modifier.weight(1f),
                                onValueChange = {value -> if(value!=null){
                                    newItemQuantityForService = value.filter { it.isDigit() }
                                }else newItemQuantityForService="0"
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f)) {
                                Text(text = "Is this input:")
                                Checkbox(checked = newItemForServiceInput, onCheckedChange = {newItemForServiceInput=!newItemForServiceInput})
                            }
                        }
                        Text(text = "Price of item: ${state.chosenItemForCardItem!!.price.toString()}€", modifier = Modifier
                            .height(50.dp)
                            .padding(top = 20.dp),
                            fontSize = 18.sp)
                    }else{
                        Text(text = "Item with scanned code does not exist ${state.chosenItemForCardItem}")
                    }                    }
            })
        }






        if(manualAddDialog){
            AlertDialog(onDismissRequest = {
                manualAddDialog=false
                clearValues()
                viewModel.onEvent(StorageCardPageEvent.chosenItemForStorageCard(null))},
            confirmButton = { Button(onClick = {
                if(newItemQuantityForSale.toInt()!=0 || newItemQuantityForService.toInt()!=0){
                    viewModel.onEvent(StorageCardPageEvent.addStorageCardItemOnCard(
                        quantityForSale = newItemQuantityForSale.toInt(),
                        quantityForService = newItemQuantityForService.toInt(),
                        forServiceInput = newItemForServiceInput,
                        forSaleInput = newItemForSaleInput
                    ))
                    viewModel.onEvent(StorageCardPageEvent.chosenItemForStorageCard(null))
                    manualAddDialog=false
                    clearValues()
                }
            }) {

                Text(text = "Add")
            }},
            dismissButton = { Button(onClick = { manualAddDialog=false
                clearValues()
                viewModel.onEvent(StorageCardPageEvent.chosenItemForStorageCard(null))}) {
                Text(text = "Cancel")
            }},
            title = { Text(text = "Manually add item by ID")},
            text = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {

                    if(state.chosenItemForCardItem!=null){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(value = newItemQuantityForSale, label = { Text(text = "Quantity fo sale")},
                                maxLines = 1, singleLine = true, modifier = Modifier.weight(1f),
                                onValueChange = {value ->  if(value!=null){
                                    newItemQuantityForSale = value.filter { it.isDigit() }
                                }else newItemQuantityForSale="0"   },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f)) {
                                Text(text = "Is this input:")
                                Checkbox(checked = newItemForSaleInput, onCheckedChange = {newItemForSaleInput=!newItemForSaleInput})
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(value = newItemQuantityForService, label = { Text(text = "Quantity fo service")},
                                maxLines = 1, singleLine = true, modifier = Modifier.weight(1f),
                                onValueChange = {value -> if(value!=null){
                                    newItemQuantityForService = value.filter { it.isDigit() }
                                }else newItemQuantityForService="0"
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.weight(1f)) {
                                Text(text = "Is this input:")
                                Checkbox(checked = newItemForServiceInput, onCheckedChange = {newItemForServiceInput=!newItemForServiceInput})
                            }
                        }
                        Text(text = "Price of item: ${state.chosenItemForCardItem!!.price.toString()}€", modifier = Modifier
                            .height(50.dp)
                            .padding(top = 20.dp),
                        fontSize = 18.sp)
                    }else{
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                            OutlinedTextField(value = state.searchQuery, onValueChange = {
                                viewModel.onEvent(StorageCardPageEvent.OnSearchQueryChange(it))

                            }, modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                                placeholder = {
                                    Text(text = "Search...", maxLines = 1)
                                },
                            trailingIcon = {
                                IconButton(onClick = { itemsDropDownMenu = true}) {
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Expand dropdown menu")
                                }
                            })
                            DropdownMenu(expanded = itemsDropDownMenu, onDismissRequest = { itemsDropDownMenu=false }) {
                                state.items.forEach { item->
                                    DropdownMenuItem(text = { SingleItem(item = item) },
                                        onClick = {
                                            viewModel.onEvent(StorageCardPageEvent.OnSearchQueryChange(item.itemId))
                                            viewModel.onEvent(StorageCardPageEvent.chosenItemTemp(item))
                                            itemsDropDownMenu=false })
                                }
                            }
                            if(state.chosenItemTemp!=null){
                                Button(onClick = {
                                    viewModel.onEvent(StorageCardPageEvent.chosenItemForStorageCard(state.chosenItemTemp!!))
                                    viewModel.onEvent(StorageCardPageEvent.chosenItemTemp(null))
                                }) {
                                    Text(text = "Confirm selected item")
                                }
                            }
                        }
                    }
                }
                })
        }

    }


}