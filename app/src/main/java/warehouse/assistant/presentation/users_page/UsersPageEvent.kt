package warehouse.assistant.presentation.users_page

import warehouse.assistant.domain.model.AuthorizedUser

sealed class UsersPageEvent{
    data class changeUsersRole(val user:AuthorizedUser,val role:String):UsersPageEvent()
}
