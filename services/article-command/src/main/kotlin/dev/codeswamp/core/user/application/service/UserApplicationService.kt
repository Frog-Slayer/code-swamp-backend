package dev.codeswamp.core.user.application.service

import dev.codeswamp.core.user.application.dto.SignUpCommand
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.presentation.dto.request.NicknameChangeRequestDto

interface UserApplicationService {
    //회원정보 완성
    fun signUp(command: SignUpCommand): User

    fun modifyUserNickname(user: User, nicknameDto: NicknameChangeRequestDto)

    fun modifyUserProfileImage(user: User, profileImageUrl: String?)
}