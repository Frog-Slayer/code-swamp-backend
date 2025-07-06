package dev.codeswamp.user.presentation.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.user.application.usecase.profile.UserPublicProfile

data class UserPublicProfileResponse (
    @JsonSerialize(using = ToStringSerializer::class)
    val userId: Long?,
    val username: String?,
    val nickname: String?,
    val profileImage: String?
){
    companion object {
        fun from(profile: UserPublicProfile) = UserPublicProfileResponse(
            userId = profile.userId,
            username = profile.username ,
            nickname = profile.nickname,
            profileImage = profile.profileImage
        )
    }

}