package dev.codeswamp.user.application.usecase.register

interface RegisterUserUseCase {
    suspend fun handle(command: RegisterUserCommand): RegisterUserResult
}