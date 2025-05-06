package dev.codeswamp.core.user.domain.model

class Username private constructor(val username: String) {
    companion object {
        fun of(username: String): Username {
            require(isValidNickname(username)) {
                "username $username is not valid"
            }

            return Username(username)
        }

        private fun isValidNickname(username: String): Boolean {
            return username.length in 2..12 && username.matches("^[a-zA-Z0-9]*$".toRegex())
        }
    }
}