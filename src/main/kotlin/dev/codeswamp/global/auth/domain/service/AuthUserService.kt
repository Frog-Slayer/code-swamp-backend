package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.token.Subject

interface AuthUserService {
    fun save(authUser: AuthUser) : AuthUser
    fun findById(id: Long): AuthUser?
    fun findByUsername(username: String): AuthUser?
    fun findBySubject(subject: Subject): AuthUser?
}