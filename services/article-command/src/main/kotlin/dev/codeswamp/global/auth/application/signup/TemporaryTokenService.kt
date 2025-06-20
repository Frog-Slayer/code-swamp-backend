package dev.codeswamp.global.auth.application.signup

import org.springframework.stereotype.Service
import java.util.*

@Service
class TemporaryTokenService(
    private val temporaryTokenStorage: TemporaryTokenStorage,
) {
    private val timeToAllowRegistration : Long  = 10

    fun generateSignupToken(email: String): String {
        val token = UUID.randomUUID().toString()
        temporaryTokenStorage.save(token, email, timeToAllowRegistration)
        return token
    }

    fun authenticate(temporaryToken: String, email: String) : Boolean {
        val savedEmail = temporaryTokenStorage.get(temporaryToken)
        return savedEmail == email
    }

    fun deleteTemporaryToken(temporaryToken: String) {
        temporaryTokenStorage.delete(temporaryToken)
    }
}