package dev.codeswamp.core.user.domain.model

class Nickname private constructor(val nickname: String) {
    companion object {
        fun of(nickname: String): Nickname {
            require(isValidNickname(nickname)) {
                "Nickname $nickname is not valid"
            }

            return Nickname(nickname)
        }

        private fun isValidNickname(nickname: String): Boolean {
            return nickname.length in 2..12 && nickname.matches("^[a-zA-Z0-9]*$".toRegex())
        }
    }
}