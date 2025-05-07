package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser

interface UserFinder {
    fun findBySubject(subject: String): AuthUser?
    fun findById(id: Long): AuthUser?
}