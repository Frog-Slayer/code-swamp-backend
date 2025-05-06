package dev.codeswamp.global.auth.infrastructure.adapter.jwt.service

import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken
import dev.codeswamp.global.auth.domain.service.TokenValidationService
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class JwtValidator(
    //private val userRepository: UserRepository
) : TokenValidationService{
    override fun validateAccessToken(accessToken: AccessToken) {
        TODO("Not yet implemented")
    }

    override fun validateRefreshToken(refreshToken: RefreshToken) {
        TODO("Not yet implemented")
    }
}