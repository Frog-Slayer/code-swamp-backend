package dev.codeswamp.user.application.usecase.register

import dev.codeswamp.user.application.event.AuthUserRollbackRequestedEvent
import dev.codeswamp.user.application.exception.InvalidSignupTokenException
import dev.codeswamp.user.application.port.outgoing.SignupTokenVerifier
import dev.codeswamp.user.application.port.outgoing.messaging.EventPublisher
import dev.codeswamp.user.application.transaction.UserTransactionalService
import org.springframework.stereotype.Service

@Service
class RegisterUserUseCaseImpl(
    private val userTransactionalService: UserTransactionalService,
    private val eventPublisher: EventPublisher,
    private val signupTokenVerifier: SignupTokenVerifier,
) : RegisterUserUseCase {

    override suspend fun handle(command: RegisterUserCommand): RegisterUserResult {

        try {
            val verificationResult = signupTokenVerifier.verifyTokenAndCreateUser(command.token, command.email)

            val user = userTransactionalService.create(
                verificationResult.userId,
                command.username,
                command.nickname,
                command.profileImageUrl
            )

            val events = user.pullEvents()
            events.forEach { eventPublisher.publish(it) }

            return RegisterUserResult(
                otp = verificationResult.otp
            )
        } catch (e: InvalidSignupTokenException) {//authUser가 만들어지지 않았음이 확실한 경우
            throw e
        } catch (e: Exception) {//그외 : 보상
            eventPublisher.publish(
                AuthUserRollbackRequestedEvent(
                    command.email,
                )
            )
            throw e
        }
    }
}