package dev.codeswamp.global.auth.application.signup

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TemporaryTokenService(
    private val temporaryTokenStore: TemporaryTokenStore
) {
    private val timeToAllowRegistration : Long  = 10

    fun generateSignupToken(email: String): String {
        val token = UUID.randomUUID().toString()
        temporaryTokenStore.save(token, email, timeToAllowRegistration)
        return token
    }

    fun authenticate(signupToken: String, email: String) : Boolean {
        val savedEmail = temporaryTokenStore.get(signupToken)
        return savedEmail == email
    }
}