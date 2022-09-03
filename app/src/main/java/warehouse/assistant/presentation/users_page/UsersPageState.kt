package warehouse.assistant.presentation.users_page

import warehouse.assistant.domain.model.AuthorizedUser

data class UsersPageState(
    val users:List<AuthorizedUser> = emptyList(),
    val disabledRoles:List<String> = emptyList(),
    val roles: List<String> = listOf("owner","admin","worker","fired")
)
