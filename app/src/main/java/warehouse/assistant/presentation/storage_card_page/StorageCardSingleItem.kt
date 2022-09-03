package warehouse.assistant.presentation.storage_card_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import warehouse.assistant.domain.model.StorageCardItem

@Composable
fun StorageCardSingleItem(
    storageCardItem:StorageCardItem
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Text(text = storageCardItem.itemId)
        }
        Box(modifier = Modifier.weight(1f),contentAlignment = Alignment.Center) {
            Text(text = if (storageCardItem.forSaleInput) "${storageCardItem.quantityForSale}" else "-${storageCardItem.quantityForSale}")
        }
        Box(modifier = Modifier.weight(1f),contentAlignment = Alignment.Center) {
            Text(text = if (storageCardItem.forServiceInput) "${storageCardItem.quantityForService}" else "-${storageCardItem.quantityForService}")
        }
        Box(modifier = Modifier.weight(1f),contentAlignment = Alignment.Center) {
            Text(text = storageCardItem.price.toString())
        }
    }
}