package dev.codeswamp.user.application.exception

import dev.codeswamp.core.common.exception.NotFoundException

class UserNotFoundException(
    message: String,
) : AppNotFoundException(errorCode = AppNotFoundErrorCode.USER_NOT_FOUND, message = "User not found"){
    companion object {
        fun byId(id: Long) = UserNotFoundException("User with ID $id")
        fun byUsername(username: String) = UserNotFoundException("User with name $username not found")
    }
}