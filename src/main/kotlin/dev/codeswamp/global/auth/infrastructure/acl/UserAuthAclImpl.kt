package dev.codeswamp.global.auth.infrastructure.acl

import dev.codeswamp.core.user.application.acl.AuthServiceAcl
import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.application.signup.TemporaryTokenService
import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.Role
import org.springframework.stereotype.Component

@Component
class UserAuthAclImpl(
    private val temporaryTokenService: TemporaryTokenService,
    private val authApplicationService: AuthApplicationService
) : AuthServiceAcl {
    override fun verifyTokenAndCreateAuthUser(signupToken: String, email: String): Long {
        if (!temporaryTokenService.authenticate(signupToken, email)) throw IllegalStateException("Invalid or expired signup token")

        return authApplicationService.save(AuthUser(username = email, role = Role.USER)).id
            ?: throw java.lang.IllegalStateException("Failed to create user")
    }
}