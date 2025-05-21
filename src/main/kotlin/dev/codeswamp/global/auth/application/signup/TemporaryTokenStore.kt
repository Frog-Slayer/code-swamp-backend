package dev.codeswamp.global.auth.application.signup

interface TemporaryTokenStore {
    fun save(token: String, email: String, timeToLiveInMinutes: Long)
    fun get(token: String): String?
}