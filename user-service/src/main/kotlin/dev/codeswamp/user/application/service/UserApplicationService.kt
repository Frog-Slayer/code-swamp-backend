package dev.codeswamp.user.application.service

import dev.codeswamp.user.application.dto.SignUpCommand
import dev.codeswamp.user.domain.model.User
import dev.codeswamp.user.presentation.dto.request.NicknameChangeRequestDto

interface UserApplicationService {
    //회원정보 완성
    fun signUp(command: SignUpCommand): User

    fun modifyUserNickname(user: User, nicknameDto: NicknameChangeRequestDto)

    fun modifyUserProfileImage(user: User, profileImageUrl: String?)
}