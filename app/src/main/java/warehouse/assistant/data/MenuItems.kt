package warehouse.assistant.data

import warehouse.assistant.domain.model.MenuItem
import warehouse.assistant.presentation.destinations.ItemsPageDestination
import warehouse.assistant.presentation.destinations.StoragesPageDestination
import warehouse.assistant.R
import warehouse.assistant.data.remote.dto.FirebaseAuthImpl
import warehouse.assistant.presentation.destinations.LoginPageDestination
import warehouse.assistant.presentation.destinations.UsersPageDestination

object MenuItems {
    val itemsPageMenu:MenuItem =
        MenuItem("items","Items","Go to items page",
            R.drawable.ic_box_package_icon, ItemsPageDestination)

    val storagesPageMenu:MenuItem =MenuItem("storages","Storages","Go to storages page",
            R.drawable.ic_goods_contents_icon, StoragesPageDestination)

    val loginPage:MenuItem = MenuItem("login","Login","go to login page",
            R.drawable.ic_box_package_icon,LoginPageDestination)

    val usersPage:MenuItem = MenuItem("users","Users","go to users page",
    R.drawable.ic_users_icon,UsersPageDestination)

    val adminMenu:List<MenuItem> = listOf(itemsPageMenu, storagesPageMenu, usersPage)

    val workerMenu:List<MenuItem> = listOf(itemsPageMenu, storagesPageMenu)

    fun getMenu():List<MenuItem>{
        var role = FirebaseAuthImpl.getUser().role
        if(role=="admin" ||role=="owner") return adminMenu
        else if (role=="worker") return workerMenu
        else return emptyList()
    }
}