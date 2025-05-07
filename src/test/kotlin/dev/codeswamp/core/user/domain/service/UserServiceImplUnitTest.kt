package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@DisplayName("UserService 단위 테스트")
class UserServiceImplUnitTest {

    private lateinit var userService:  UserService;
    private lateinit var userRepository: UserRepository;
    private lateinit var user : User

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        userService = UserServiceImpl(userRepository)

        user = User(
            id = null,
            username = Username.of("name"),
            email = "test@email.com",
            nickname = Nickname.of("nick"),
            profileUrl = null,
            role = Role.GUEST
        )
    }

    @Test
    fun `사용자 저장` () {
        every { userRepository.save(user) } returns user.copy(id = 1L)

        val savedUser = userService.save(user)
        assertThat(savedUser.id).isEqualTo(1L)

        verify(exactly = 1) { userRepository.save(user) }
    }

    @Test
    fun `이메일 조회`() {
        val email = "test@email.com"
        every { userRepository.findByEmail(email) } returns user.copy(id = 1L)

        val foundUser = userService.findUserByEmail(email)

        assertThat(foundUser).isNotNull()
        assertThat(foundUser?.email).isEqualTo(email)
        assertThat(foundUser?.id).isEqualTo(1L)

        verify(exactly = 1) { userRepository.findByEmail(email) }
    }

    @Test
    fun `닉네임 조회`() {
        val nickname = Nickname.of("nick")
        every { userRepository.findByNickname( nickname) } returns user.copy(id = 1L)

        val foundUser = userService.findUserByNickname(nickname)

        assertThat(foundUser).isNotNull()
        assertThat(foundUser?.nickname).isEqualTo(nickname)
        assertThat(foundUser?.id).isEqualTo(1L)

        verify(exactly = 1) { userRepository.findByNickname(nickname) }
    }

    @Test
    fun `닉네임 길이 검증`() {
        val validNickname = "nick"
        val shortNickname = "n"
        val longNickname= "thismustbetoolongnickname"

        assertThatThrownBy { Nickname.of(shortNickname) }
            .isInstanceOf(IllegalArgumentException::class.java)

        assertThatThrownBy { Nickname.of(longNickname) }
            .isInstanceOf(IllegalArgumentException::class.java)


        assertDoesNotThrow { Nickname.of(validNickname) }

        val nickname = Nickname.of(validNickname)
        assertThat(nickname.value).isEqualTo(validNickname)
    }
}