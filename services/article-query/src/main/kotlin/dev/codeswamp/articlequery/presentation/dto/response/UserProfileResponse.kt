package dev.codeswamp.articlequery.presentation.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.articlequery.application.readmodel.model.UserProfile

data class UserProfileResponse (
    @JsonSerialize(using = ToStringSerializer::class)
    val userId: Long?,
    val username: String?,
    val nickname: String?,
    val profileImage: String?
) {
    companion object {
        fun from (userProfile: UserProfile?) = UserProfileResponse(
            userId = userProfile?.userId,
            username = userProfile?.username,
            nickname = userProfile?.nickname,
            profileImage = userProfile?.profileImage
        )
    }
}