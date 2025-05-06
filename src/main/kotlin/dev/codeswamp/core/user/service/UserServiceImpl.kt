package dev.codeswamp.core.user.service

import dev.codeswamp.core.user.entity.User
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    override fun getUserByEmail(email: String): User {
        TODO("Not yet implemented")
    }

    override fun getUserByNickname(nickname: String): User {
        TODO("Not yet implemented")
    }

    override fun modifyProfile(user: User, profileUrl: String?) {
        TODO("Not yet implemented")
    }

    override fun modifyNickname(user: User, nickname: String) {
        TODO("Not yet implemented")
    }

    override fun isValidNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isDuplicateNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }
}