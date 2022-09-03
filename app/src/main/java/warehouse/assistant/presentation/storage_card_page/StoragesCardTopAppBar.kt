package warehouse.assistant.presentation.storage_card_page
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun StorageCardPageTopAppBar(
    onNavigationIconClick: () -> Unit,
    storageName:String
){
    CenterAlignedTopAppBar(
        title = {
            Text(text = "$storageName card")
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = {
                onNavigationIconClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack,contentDescription = "Go back to storages")
            }
        }
    )
}