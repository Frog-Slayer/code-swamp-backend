package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.User

interface UserService {
    fun signUp(user: User)

    fun findUserByEmail(email: String): User
    fun findUserByUsername(username: String): User
    fun findUserByNickname(nickname: String): User

    fun modifyProfile(user: User, profileUrl: String? = null)
    fun modifyNickname(user: User, nickname: String)

    fun isValidUsername(username: String): Boolean
    fun isValidNickname(nickname: String): Boolean
    fun isDuplicateNickname(nickname: String): Boolean

}