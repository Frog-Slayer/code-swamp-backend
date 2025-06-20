package dev.codeswamp.user.application.usecase.register

import dev.codeswamp.user.application.event.AuthUserRollbackRequestedEvent
import dev.codeswamp.user.application.exception.InvalidSignupTokenException
import dev.codeswamp.user.application.port.outgoing.EventPublisher
import dev.codeswamp.user.application.port.outgoing.SignupTokenVerifier
import dev.codeswamp.user.application.transaction.UserTransactionalService
import dev.codeswamp.user.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class RegisterUserUseCaseImpl(
    private val userTransactionalService: UserTransactionalService,
    private val eventPublisher: EventPublisher,
    private val signupTokenVerifier: SignupTokenVerifier,
) : RegisterUserUseCase {

    override suspend fun handle(command: RegisterUserCommand) {
        try {
            val authUserId = signupTokenVerifier.verifyTokenAndCreateUser(command.token, command.email)//GRPC

            val user = userTransactionalService.create(
                authUserId,
                command.username,
                command.nickname,
                command.profileImageUrl
            )

            val events = user.pullEvents()
            events.forEach {  eventPublisher.publish(it) }
        } catch (e : InvalidSignupTokenException) {//authUser가 만들어지지 않았음이 확실한 경우
            throw e
        } catch (e : Exception) {//그외 : 보상
            eventPublisher.publish(AuthUserRollbackRequestedEvent(
                command.email,
            ))
            throw e
        }
    }
}