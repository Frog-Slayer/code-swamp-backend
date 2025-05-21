package dev.codeswamp.core.user.application.service

import dev.codeswamp.core.user.application.dto.SignUpCommand
import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.service.UserService
import dev.codeswamp.core.user.presentation.dto.request.NicknameChangeRequestDto
import dev.codeswamp.core.user.presentation.dto.request.SignUpRequestDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserApplicationServiceImpl(
    private val userService: UserService,
) : UserApplicationService {

    @Transactional
    override fun signUp(command: SignUpCommand): User {
        requireNotNull(command.username ) { "$command.username is required" }
        requireNotNull(command.nickname ) { "$command.nickname is required" }

        val signUpUser =  User(
                username = Username.of(command.username),
                nickname = Nickname.of(command.nickname),
                profileUrl = command.profileImageUrl,
        )

        //TODO 루트 폴더 생성

        return userService.save(signUpUser)
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