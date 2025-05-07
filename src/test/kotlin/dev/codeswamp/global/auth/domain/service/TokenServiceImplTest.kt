package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.service.UserService
import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.util.TokenParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@DisplayName("TokenService 단위 테스트")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenServiceImplTest (
    @Autowired private val tokenParser: TokenParser,
    @Autowired private val tokenService: TokenService,
    @Autowired private val userFinder: UserFinder,
    @Autowired private val userService: UserService
){

    private lateinit var user1: User
    private lateinit var authUser: AuthUser

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
        userService.save(user1).id
    }

    @BeforeEach
    fun findAuthUserTest() {
        authUser = assertNotNull(userFinder.findById(1L)!!)
        assertThat(authUser.id).isEqualTo(1L)
    }

    @Test
    fun issueAccessToken() {
        val accessToken = tokenService.issueAccessToken(authUser)

        assertNotNull(accessToken)
        assertThat(accessToken.authUser.id).isEqualTo(authUser.id)
        assertThat(accessToken.authUser.username).isEqualTo(authUser.username)
    }

    @Test
    fun issueRefreshToken() {
        val accessToken = tokenService.issueRefreshToken(authUser)

        assertNotNull(accessToken)
        assertThat(accessToken.authUser.id).isEqualTo(authUser.id)
        assertThat(accessToken.authUser.username).isEqualTo(authUser.username)
    }

    @Test
    fun validateAccessToken() {
        //토큰 생성
        val accessToken = tokenService.issueAccessToken(authUser)
        val tokenVal = accessToken.value

        assertThat(tokenVal).isNotNull()

        //raw 토큰을 생성
        val rawToken = tokenParser.parseAccessToken(tokenVal)

        assertThat(rawToken).isNotNull()
        assertThat(rawToken.value).isEqualTo(accessToken.value)

        //validate
        val validatedToken = tokenService.validateAccessToken(rawToken)
        assertThat(accessToken).isEqualTo(validatedToken)
    }

    @Test
    fun validateRefreshToken() {
        //토큰 생성
        val refreshToken = tokenService.issueRefreshToken(authUser)

        //토큰 저장
        tokenService.storeRefreshToken(refreshToken)


        val tokenVal = refreshToken.value

        assertThat(tokenVal).isNotNull()

        //raw 토큰을 생성
        val rawToken = tokenParser.parseRefreshToken(tokenVal)

        assertThat(rawToken).isNotNull()
        assertThat(rawToken.value).isEqualTo(refreshToken.value)


        //validate
        val validatedToken = tokenService.validateRefreshToken(rawToken)
        assertThat(refreshToken).isEqualTo(validatedToken)
    }

}