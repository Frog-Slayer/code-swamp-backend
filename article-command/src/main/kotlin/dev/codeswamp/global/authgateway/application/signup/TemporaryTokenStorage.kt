package dev.codeswamp.global.authgateway.application.signup

interface TemporaryTokenStorage {
    fun save(token: String, email: String, timeToLiveInMinutes: Long)
    fun get(token: String): String?
    fun delete(token: String)
}