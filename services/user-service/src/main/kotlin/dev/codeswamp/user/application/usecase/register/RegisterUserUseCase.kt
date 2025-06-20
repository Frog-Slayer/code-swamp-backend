package dev.codeswamp.user.application.usecase.register

import dev.codeswamp.user.domain.user.model.User

interface RegisterUserUseCase {
    suspend fun handle(command: RegisterUserCommand) : RegisterUserResult
}