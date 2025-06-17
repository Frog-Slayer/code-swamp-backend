package dev.codeswamp.auth.application.signup

interface TemporaryTokenStorage {
    suspend fun save(token: String, email: String, timeToLiveInMinutes: Long)
    suspend fun get(token: String): String?
    suspend fun delete(token: String)
}