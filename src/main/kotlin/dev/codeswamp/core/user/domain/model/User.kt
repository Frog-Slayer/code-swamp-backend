package dev.codeswamp.core.user.domain.model


data class User (
    val id: Long? = null,
    val username: Username,//변경 불가(사용자 ID)
    val email: String,
    var nickname: Nickname,
    var profileUrl: String? = null,
    val role: Role,
) {
    fun modifyNickname(nickname: Nickname) {
        this.nickname = nickname
    }

    fun modifyProfileUrl(profileUrl: String) {
        this.profileUrl = profileUrl
    }
}