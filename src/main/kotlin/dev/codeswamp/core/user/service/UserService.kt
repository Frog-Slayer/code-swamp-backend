package dev.codeswamp.core.user.service

import dev.codeswamp.core.user.infrastructure.entity.UserEntity

interface UserService {
    fun getUserByEmail(email: String): UserEntity
    fun getUserByNickname(nickname: String): UserEntity

    fun modifyProfile(user: UserEntity, profileUrl: String? = null)
    fun modifyNickname(user: UserEntity, nickname: String)

    fun isValidNickname(nickname: String): Boolean
    fun isDuplicateNickname(nickname: String): Boolean

}