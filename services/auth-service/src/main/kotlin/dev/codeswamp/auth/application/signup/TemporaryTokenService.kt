package dev.codeswamp.auth.application.signup

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class TemporaryTokenService(
    private val temporaryTokenStorage: TemporaryTokenStorage,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val timeToAllowRegistration: Long = 10
    private val timeToAllowAutoLogin: Long = 1

    suspend fun generateSignupToken(email: String): String {
        val token = UUID.randomUUID().toString()
        temporaryTokenStorage.save(token, email, timeToAllowRegistration)
        return token
    }

    suspend fun generateOtp(email: String): String {
        val token = UUID.randomUUID().toString()
        temporaryTokenStorage.save(token, email, timeToAllowAutoLogin)
        return token
    }

    suspend fun authenticate(temporaryToken: String, email: String): Boolean {
        val savedEmail = temporaryTokenStorage.getAndDelete(temporaryToken)
        logger.info("saved email: $savedEmail")
        return savedEmail == email
    }
}