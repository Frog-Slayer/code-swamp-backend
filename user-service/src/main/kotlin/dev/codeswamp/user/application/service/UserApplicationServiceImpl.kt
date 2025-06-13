package dev.codeswamp.user.application.service

import dev.codeswamp.user.application.acl.FolderAcl
import dev.codeswamp.user.application.dto.SignUpCommand
import dev.codeswamp.user.domain.model.Nickname
import dev.codeswamp.user.domain.model.User
import dev.codeswamp.user.domain.model.Username
import dev.codeswamp.user.domain.service.UserService
import dev.codeswamp.user.presentation.dto.request.NicknameChangeRequestDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserApplicationServiceImpl(
    private val userService: UserService,
    private val folderAcl: FolderAcl,
) : UserApplicationService {

    @Transactional
    override fun signUp(command: SignUpCommand): User {
        requireNotNull(command.username ) { "$command.username is required" }
        requireNotNull(command.nickname ) { "$command.nickname is required" }

        val signUpUser = User(
            username = Username.Companion.of(command.username),
            nickname = Nickname.Companion.of(command.nickname),
            profileUrl = command.profileImageUrl,
        )

        val user = userService.save(signUpUser)
        val userId = requireNotNull(user.id)

        folderAcl.createRootFolder(userId, command.username)
        return user
    }

    @Transactional
    override fun modifyUserNickname(
        user: User,
        nicknameDto: NicknameChangeRequestDto
    ) {
        user.nickname = Nickname.Companion.of(nicknameDto.nickname)
        userService.save(user)//명시적 호출 필요
    }

    @Transactional
    override fun modifyUserProfileImage(user: User, profileImageUrl: String?) {
        user.profileUrl = profileImageUrl
        userService.save(user)//명시적 호출 필요
    }
}