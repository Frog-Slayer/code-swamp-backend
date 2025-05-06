package dev.codeswamp.core.user.service

import dev.codeswamp.core.user.infrastructure.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    override fun getUserByEmail(email: String): UserEntity {
        TODO("Not yet implemented")
    }

    override fun getUserByNickname(nickname: String): UserEntity {
        TODO("Not yet implemented")
    }

    override fun modifyProfile(user: UserEntity, profileUrl: String?) {
        TODO("Not yet implemented")
    }

    override fun modifyNickname(user: UserEntity, nickname: String) {
        TODO("Not yet implemented")
    }

    override fun isValidNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isDuplicateNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }
}