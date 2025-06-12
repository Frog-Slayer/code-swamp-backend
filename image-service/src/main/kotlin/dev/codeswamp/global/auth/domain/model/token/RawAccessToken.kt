package dev.codeswamp.global.auth.domain.model.token

import java.time.Instant

enum class SubjectType {
    USERNAME
}

data class Subject(val type: SubjectType = SubjectType.USERNAME, val value: String)

data class RawAccessToken(
    val value: String,
    val sub: Subject,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}