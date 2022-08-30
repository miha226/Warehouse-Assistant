package warehouse.assistant.presentation.app_navigation

import android.content.ContentValues
import android.util.Log
import android.util.Range
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import warehouse.assistant.R


@OptIn(ExperimentalMaterial3Api::class)
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
                Log.d(ContentValues.TAG,"Clicked on menu item proslo 2")
                onGetItemsFromFirebaseClick()
            }) {
               Icon(painterResource(id =R.drawable.ic_package_replacement_icon ), contentDescription = "Get items from firebase")
            }
        }
    )
}