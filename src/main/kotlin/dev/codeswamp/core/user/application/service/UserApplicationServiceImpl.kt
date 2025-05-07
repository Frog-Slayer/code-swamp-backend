package dev.codeswamp.core.user.application.service

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.service.UserService
import dev.codeswamp.core.user.presentation.dto.request.NicknameChangeRequestDto
import dev.codeswamp.core.user.presentation.dto.request.UserSignUpRequestDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserApplicationServiceImpl(
    private val userService: UserService,
) : UserApplicationService {

    @Transactional
    override fun signUp(user: User, dto: UserSignUpRequestDto): User {
        requireNotNull(dto.username ) { "$dto.username is required" }
        requireNotNull(dto.nickname ) { "$dto.nickname is required" }

        val signUpUser =  user.copy(
                username = Username.of(dto.username),
                nickname = Nickname.of(dto.nickname),
                profileUrl = dto.profileImageUrl,
                role = Role.USER
        )

        userService.save(signUpUser)

        //TODO 루트 폴더 생성

        return signUpUser
    }

    @Transactional
    override fun modifyUserNickname(
        user: User,
        nicknameDto: NicknameChangeRequestDto
    ) {
        user.nickname = Nickname.of(nicknameDto.nickname)
        userService.save(user)//명시적 호출 필요
    }

    @Transactional
    override fun modifyUserProfileImage(user: User, profileImageUrl: String?) {
        user.profileUrl = profileImageUrl
        userService.save(user)//명시적 호출 필요
    }
}