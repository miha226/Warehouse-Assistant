package warehouse.assistant.data.mapper

import warehouse.assistant.data.local.AuthorizedUserEntity
import warehouse.assistant.domain.model.AuthorizedUser

fun AuthorizedUserEntity.toAuthorizedUser(): AuthorizedUser{
    return AuthorizedUser(
        email = email,
        username = username,
        role = role,
        time = time
    )
}

fun AuthorizedUser.toAuthorizedUserEntity(): AuthorizedUserEntity{
    return AuthorizedUserEntity(
        email = email,
        username = username,
        role = role,
        time = time
    )
}