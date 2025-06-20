package dev.codeswamp.auth.application.signup

interface TemporaryTokenStorage {
    suspend fun save(token: String, email: String, timeToLiveInMinutes: Long)
    suspend fun getAndDelete(token: String): String?
}