package dev.codeswamp.core.user.domain.model


data class User (
    val id: Long? = null,
    val username: Username,//변경 불가(사용자 ID)
    var nickname: Nickname,
    var profileUrl: String? = null,
) {
    fun modifyNickname(nickname: Nickname) {
        this.nickname = nickname
    }

    fun modifyProfileUrl(profileUrl: String) {
        this.profileUrl = profileUrl
    }
}