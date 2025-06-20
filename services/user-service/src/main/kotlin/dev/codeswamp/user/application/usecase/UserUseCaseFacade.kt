package dev.codeswamp.user.application.usecase

import dev.codeswamp.user.application.usecase.register.RegisterUserCommand
import dev.codeswamp.user.application.usecase.register.RegisterUserUseCase
import dev.codeswamp.user.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class UserUseCaseFacade(
    private val registerUserUseCase: RegisterUserUseCase,
){

    suspend fun registerUserWithAuthentication(command: RegisterUserCommand) {
        return registerUserUseCase.handle(command)
    }



}