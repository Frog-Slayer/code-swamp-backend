package dev.codeswamp.core.user.domain.model

class Nickname private constructor(val value: String?) {
    companion object {
        fun of(nickname: String?): Nickname {
            require(nickname == null || isValidNickname(nickname)) {
                "Nickname $nickname is not valid"
            }

            return Nickname(nickname)
        }

        private fun isValidNickname(nickname: String?): Boolean {
            return nickname != null && nickname.length in 2..12 && nickname.matches("^[a-zA-Z0-9가-힣]*$".toRegex())
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Nickname && other.value == this.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}