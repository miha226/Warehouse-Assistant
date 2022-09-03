package warehouse.assistant.domain.model

data class AuthorizedUser(
    val email: String = "placeholderMail",
    val username: String = "placeholderUsername",
    val role: String = "error role",
    val time: Long = 1
    )
