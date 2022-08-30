package warehouse.assistant.presentation.storages_page

import android.content.ContentValues
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import warehouse.assistant.R

@Composable
fun StoragesTopAppBar(
    onNavigationIconClick: () -> Unit
){
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Storages")
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
        }
    )
}