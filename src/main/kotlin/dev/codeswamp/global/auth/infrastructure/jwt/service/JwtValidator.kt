package dev.codeswamp.global.auth.infrastructure.jwt.service

import dev.codeswamp.core.user.domain.repository.UserRepository
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.service.TokenValidationService
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class JwtValidator(
    private val userRepository: UserRepository
) : TokenValidationService{
    override fun validateAccessToken(accessToken: ValidatedAccessToken) {
        require(!accessToken.expired()) {"Access token is expired"}
        //TODO requireNotNull( userRepository.findById(accessToken.authUser.id))
    }

    override fun validateRefreshToken(refreshToken: RawRefreshToken) {
        require(!refreshToken.expired()) {"Refresh token is expired"}
    }
}