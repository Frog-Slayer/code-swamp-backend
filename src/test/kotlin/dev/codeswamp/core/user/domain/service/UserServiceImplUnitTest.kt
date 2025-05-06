package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserService 단위 테스트")
class UserServiceImplUnitTest {

    private lateinit var userService: UserService;
    private lateinit var userRepository: UserRepository;

    @BeforeEach
    fun setUp() {
    }
}