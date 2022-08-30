package warehouse.assistant.presentation.storages_page

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import warehouse.assistant.data.DateFormater
import warehouse.assistant.presentation.app_navigation.Drawer
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun StoragesPage(
    navigator: DestinationsNavigator

){
    Drawer(navigateTo =  {
        navigator.navigate(it)
    }) {
       StoragesScaffold(drawerState = it) {
           LazyColumn{
               item{
                   var milis = DateFormater.getCurrentMillis()
                   var date = DateFormater.getDateFromMillis(milis)
                   Text(text = milis.toString())
                   Text(text = date)
                   //Text(text = DateFormater.getMillisFromDate(date).toString())
               }
               item(key = "addStorageButton"){
                   Card(onClick = { /*TODO*/ }) {
                       Text(text = "Add Storage")
                   }
               }
           }
       }
    }

}