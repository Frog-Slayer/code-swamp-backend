package dev.codeswamp.user.application.usecase

import dev.codeswamp.user.application.usecase.register.RegisterUserCommand
import dev.codeswamp.user.application.usecase.register.RegisterUserResult
import dev.codeswamp.user.application.usecase.register.RegisterUserUseCase
import org.springframework.stereotype.Service

@Service
class UserUseCaseFacade(
    private val registerUserUseCase: RegisterUserUseCase,
) {

    suspend fun registerUserWithAuthentication(command: RegisterUserCommand): RegisterUserResult {
        return registerUserUseCase.handle(command)
    }


}