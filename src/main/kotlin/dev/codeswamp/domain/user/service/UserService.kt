package dev.codeswamp.domain.user.service

import dev.codeswamp.domain.user.entity.User

interface UserService {
    fun getUserByEmail(email: String): User
    fun getUserByNickname(nickname: String): User

    fun modifyProfile(user: User, profileUrl: String? = null)
    fun modifyNickname(user: User, nickname: String)

    fun isValidNickname(nickname: String): Boolean
    fun isDuplicateNickname(nickname: String): Boolean

}