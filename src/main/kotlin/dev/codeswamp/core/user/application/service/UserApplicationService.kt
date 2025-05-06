package dev.codeswamp.core.user.application.service

import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.presentation.dto.request.UserSignUpRequestDto

interface UserApplicationService {
    //회원정보 완성
    fun signUp(dto: UserSignUpRequestDto): User

    //

}