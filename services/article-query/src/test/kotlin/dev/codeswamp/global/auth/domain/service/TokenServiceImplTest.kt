package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.Role
import dev.codeswamp.global.auth.domain.util.TokenParser
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@DisplayName("TokenService 단위 테스트")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenServiceImplTest(
    @Autowired private val tokenParser: TokenParser,
    @Autowired private val tokenService: TokenService,
    @Autowired private val authUserService: AuthUserService,
) {

    private lateinit var user: AuthUser
    private lateinit var authUser: AuthUser

    @BeforeAll
    fun beforeAll() {
        user = AuthUser(
            id = null,
            username = "name",
            role = Role.GUEST
        )

        authUserService.save(user)
    }

    @BeforeEach
    fun findAuthUserTest() {
        authUser = assertNotNull(authUserService.findById(1L)!!)
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

    @Test
    fun rotateRefreshTokenTest() {
        val oldToken = tokenService.issueRefreshToken(authUser)
        tokenService.storeRefreshToken(oldToken)

        val rawOldToken = tokenParser.parseRefreshToken(oldToken.value)

        val newToken = tokenService.issueRefreshToken(authUser)
        tokenService.rotateRefreshToken(newToken)

        assertThatThrownBy { tokenService.validateRefreshToken(rawOldToken) }
            .isInstanceOf(IllegalStateException::class.java)
    }
}