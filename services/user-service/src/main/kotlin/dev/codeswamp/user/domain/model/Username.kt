package dev.codeswamp.user.domain.model

//TODO -> as value class
class Username private constructor(val value: String) {
    companion object {
        fun of(username: String): Username {
            require(username == null || isValidUsername(username)) {
                "username $username is not valid"
            }

            return Username(username)
        }

        private fun isValidUsername(username: String?): Boolean {
            return username != null && username.length in 2..12 && username.matches("^[a-zA-Z0-9]*$".toRegex())
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Username && other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode() ?: 0
    }
}