package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.User

interface UserService {
    fun signUp(user: User)

    fun getUserByEmail(email: String): User
    fun getUserByUsername(username: String): User
    fun getUserByNickname(nickname: String): User

    fun modifyProfile(user: User, profileUrl: String? = null)
    fun modifyNickname(user: User, nickname: String)

    fun isValidUsername(username: String): Boolean
    fun isValidNickname(nickname: String): Boolean
    fun isDuplicateNickname(nickname: String): Boolean

}