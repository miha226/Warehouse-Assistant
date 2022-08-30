package warehouse.assistant.domain.model

data class AuthorizedUser (
    val email: String,
    val username: String,
    val role: String,
    val time: Long
    )
