package dev.codeswamp.global.authgateway.domain.service

import dev.codeswamp.global.authgateway.domain.model.AuthUser
import dev.codeswamp.global.authgateway.domain.model.token.Subject

interface AuthUserService {
    fun save(authUser: AuthUser) : AuthUser
    fun findById(id: Long): AuthUser?
    fun findByUsername(username: String): AuthUser?
    fun findBySubject(subject: Subject): AuthUser?
}