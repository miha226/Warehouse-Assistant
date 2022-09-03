package warehouse.assistant.presentation.stock_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import warehouse.assistant.R

@Composable
fun StockPageTopAppBar(
    onNavigationIconClick: () -> Unit,
    storageName:String
){
    CenterAlignedTopAppBar(
        title = {
            Text(text = storageName)
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
        },
        actions = {
            Row(modifier = Modifier.height(40.dp)) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_box_package_icon), contentDescription = "Show items in storage")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_audit_report_survey_icon), contentDescription = "Show storageCards")
                }
            }

        }
    )
}