package dev.codeswamp.core.user.domain.service


import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.global.auth.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.repository.UserRepository
import dev.codeswamp.core.user.infrastructure.persistence.repository.UserJpaRepository

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.BeforeAll

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import kotlin.test.Test

@DisplayName("UserService JPA 사용 단위 테스트")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplJPAUnitTest(
    @Autowired private val userService: UserService
){
    private lateinit var user1: User
    private lateinit var user2: User

    @BeforeAll
    fun beforeAll() {
        user1 = User(
            id = null,
            username = Username.of("name"),
            nickname = Nickname.of("nick"),
            profileUrl = null,
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
    fun `닉네임 조회`() {
        val nickname = Nickname.of("nick")
        val foundUser = userService.findUserByNickname(nickname)

        assertThat(foundUser).isNotNull()
        assertThat(foundUser?.nickname).isEqualTo(nickname)
        assertThat(foundUser?.id).isEqualTo(1L)
    }

    @Test
    fun `중복 사용자명 테스트`() {
        user2 = User(
            id = null,
            username = Username.of("name"),
            nickname = Nickname.of("nick2"),
            profileUrl = null,
        )

        assertThatThrownBy { userService.save(user2) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun `중복 닉네임 테스트`() {
        user2 = User(
            id = null,
            username = Username.of("name2"),
            nickname = Nickname.of("nick"),
            profileUrl = null,
        )

        assertThatThrownBy { userService.save(user2) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun `다른 사용자 저장 테스트`() {
        user2 = User(
            id = null,
            username = Username.of("name2"),
            nickname = Nickname.of("nick2"),
            profileUrl = null,
        )

        assertDoesNotThrow{ userService.save(user2) }
    }
}