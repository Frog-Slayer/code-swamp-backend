package dev.codeswamp.core.user.domain.service


import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.repository.UserRepository
import dev.codeswamp.core.user.infrastructure.persistence.repository.UserJpaRepository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@DisplayName("UserService JPA 사용 단위 테스트")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplJPAUnitTest(
    @Autowired private val userJpaRepository: UserJpaRepository,
    @Autowired private val userRepo: UserRepository,
    @Autowired private val userService: UserService
){
    private lateinit var user1: User
    private lateinit var user2: User

    @BeforeAll
    fun beforeAll() {
        user1 = User(
            id = null,
            username = Username.of("name"),
            email = "test@email.com",
            nickname = Nickname.of("nick"),
            profileUrl = null,
            role = Role.GUEST
        )
        userService.save(user1)
    }

    @Test
    fun `사용자ID 조회` () {
        val foundUser = userService.findById(1L)

        assertThat(foundUser).isNotNull()
        assertThat(foundUser?.id).isEqualTo(1L)
    }

    @Test
    fun `이메일 조회`() {
        val email = "test@email.com"
        val foundUser = userService.findUserByEmail(email)

        assertThat(foundUser).isNotNull()
        assertThat(foundUser?.email).isEqualTo(email)
        assertThat(foundUser?.id).isEqualTo(1L)
    }

    @Test
    fun `닉네임 조회`() {
        val nickname = Nickname.of("nick")
        val foundUser = userService.findUserByNickname(nickname)

        assertThat(foundUser).isNotNull()
        assertThat(foundUser?.nickname).isEqualTo(nickname)
        assertThat(foundUser?.id).isEqualTo(1L)
    }
}