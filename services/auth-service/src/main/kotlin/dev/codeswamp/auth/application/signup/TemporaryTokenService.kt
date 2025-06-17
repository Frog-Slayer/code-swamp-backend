package dev.codeswamp.auth.application.signup

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TemporaryTokenService(
    private val temporaryTokenStorage: TemporaryTokenStorage,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val timeToAllowRegistration : Long  = 10

    suspend fun generateSignupToken(email: String): String {
        val token = UUID.randomUUID().toString()
        temporaryTokenStorage.save(token, email, timeToAllowRegistration)
        return token
    }

    suspend fun authenticate(temporaryToken: String, email: String) : Boolean {
        val savedEmail = temporaryTokenStorage.get(temporaryToken)
        logger.info("saved email: $savedEmail")
        return savedEmail == email
    }

    suspend fun deleteTemporaryToken(temporaryToken: String) {
        temporaryTokenStorage.delete(temporaryToken)
    }
}