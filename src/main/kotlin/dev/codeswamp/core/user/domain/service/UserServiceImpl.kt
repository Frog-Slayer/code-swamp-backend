package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.infrastructure.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    override fun signUp(user: User) {
        TODO("Not yet implemented")
    }

    override fun findUserByEmail(email: String): User {
        TODO("Not yet implemented")
    }

    override fun findUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun findUserByNickname(nickname: String): User {
        TODO("Not yet implemented")
    }

    override fun modifyProfile(user: User, profileUrl: String?) {
        TODO("Not yet implemented")
    }

    override fun modifyNickname(user: User, nickname: String) {
        TODO("Not yet implemented")
    }

    override fun isValidUsername(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isValidNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isDuplicateNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }

}