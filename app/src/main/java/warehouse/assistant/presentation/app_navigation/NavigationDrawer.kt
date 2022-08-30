package warehouse.assistant.presentation.app_navigation

import android.content.ContentValues
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import warehouse.assistant.data.MenuItems
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.domain.model.MenuItem
import warehouse.assistant.presentation.AuthViewModel
import warehouse.assistant.presentation.destinations.DirectionDestination
import warehouse.assistant.presentation.destinations.StoragesPageDestination

@Composable
fun DrawerHeader(
    authViewModel : AuthViewModel = hiltViewModel()
){
    val authService = authViewModel.authService
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)
        .padding(vertical = 15.dp, horizontal = 25.dp),
        contentAlignment = Alignment.Center) {
        Text(text = authService.getUserEmail(), fontSize = 40.sp, color = MaterialTheme.colorScheme.onPrimary)
    }
}


@Composable
fun DrawerBody(
 items: List<MenuItem>,
 modifier: Modifier = Modifier,
 itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
onItemClick: (MenuItem) -> Unit,
 authViewModel : AuthViewModel = hiltViewModel()
) {

    val authService = authViewModel.authService
        LazyColumn( modifier =  modifier.fillMaxSize()){
            items(items = items){ item ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(item)
                        }
                ){
                    Icon(painter = painterResource(id = item.icon), contentDescription = item.contentDescription,
                        modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = item.title, modifier.weight(1f),
                        style = itemTextStyle, fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Box(contentAlignment = Alignment.Center,modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    ElevatedButton(onClick = {
                        authService.signOut()
                        onItemClick(MenuItems.loginPage)
                        }) {
                        Text(text = "Logout", fontWeight = FontWeight.Bold,
                            fontSize = 20.sp)
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    //content: @Composable() () -> Unit,
    navigateTo: (DirectionDestination) -> Unit,
    appScaffold: @Composable() (DrawerState) -> Unit
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(drawerContent = {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxHeight(0.25f)) {
                    DrawerHeader()
                }
                Box(Modifier.fillMaxHeight()) {
                    DrawerBody(items = MenuItems.adminMenu, onItemClick = {
                        navigateTo(it.route)
                        Log.d(ContentValues.TAG,"Clicked on menu item ${it.title}")

                    })
                }

            }
    },
    drawerState = drawerState){
        /*AppScaffold(drawerState = drawerState,
            content = {content()})*/
        appScaffold(drawerState)
    }
}


