package warehouse.assistant.presentation.app_navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import warehouse.assistant.R


@Composable
fun ItemsTopAppBar(
    onNavigationIconClick: () -> Unit,
    onGetItemsFromFirebaseClick: () -> Unit
){
    CenterAlignedTopAppBar( 
        title = {
            Text(text = "Items")
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = {
                onNavigationIconClick() }) {
                Icon(imageVector = Icons.Default.Menu,contentDescription = "Toggle drawer")
            }
        },
        actions = {
            IconButton(onClick = {
                onGetItemsFromFirebaseClick()
            }) {
               Icon(painterResource(id =R.drawable.ic_package_replacement_icon ), contentDescription = "Get items from firebase")
            }
        }
    )
}