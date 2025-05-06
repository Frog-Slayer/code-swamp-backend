package dev.codeswamp.core.user.service

import dev.codeswamp.core.user.entity.User

interface UserService {
    fun getUserByEmail(email: String): User
    fun getUserByNickname(nickname: String): User

    fun modifyProfile(user: User, profileUrl: String? = null)
    fun modifyNickname(user: User, nickname: String)

    fun isValidNickname(nickname: String): Boolean
    fun isDuplicateNickname(nickname: String): Boolean

}