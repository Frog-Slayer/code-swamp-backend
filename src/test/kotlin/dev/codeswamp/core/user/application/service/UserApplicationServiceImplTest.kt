package dev.codeswamp.core.user.application.service

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.repository.UserRepository
import dev.codeswamp.core.user.domain.service.UserService
import dev.codeswamp.core.user.infrastructure.persistence.repository.UserJpaRepository
import dev.codeswamp.core.user.presentation.dto.request.UserSignUpRequestDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("User Application Service")
class UserApplicationServiceImplTest (
    @Autowired private val userJpaRepository: UserJpaRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userService: UserService,
    @Autowired private val userApplicationService: UserApplicationService
){

    @Test
    fun signUp() {
        val user = User(
            id = null,
            username = Username.of(null),
            email = "test@email.com",
            nickname = Nickname.of(null),
            profileUrl = null,
            role = Role.GUEST
        )

        val dto = UserSignUpRequestDto(
            username = "username",
            nickname = "nick",
        )

        assertDoesNotThrow { userApplicationService.signUp(user, dto) }
    }

    @Test
    fun modifyUserNickname() {
    }

    @Test
    fun modifyUserProfileImage() {
    }

}