package dev.codeswamp.user.application.usecase.register

import dev.codeswamp.framework.application.outbox.EventRecorder
import dev.codeswamp.user.application.event.AuthUserRollbackRequestedEvent
import dev.codeswamp.user.application.exception.InvalidSignupTokenException
import dev.codeswamp.user.application.port.outgoing.SignupTokenVerifier
import dev.codeswamp.user.domain.user.model.User
import dev.codeswamp.user.domain.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterUserService(
     private val eventRecorder: EventRecorder,
     private val userRepository: UserRepository
){

    @Transactional
    suspend fun create(userId: Long, username: String, nickname: String, profileImage: String?): User {
        val user = User.of(userId, username, nickname, profileImage).registered()
        eventRecorder.recordAll(user.pullEvents())
        return userRepository.insert(user)
    }

    @Transactional
    suspend fun recordCompensationTransaction(email: String) {
        eventRecorder.record (
            AuthUserRollbackRequestedEvent( email)
        )
    }
}

@Service
class RegisterUserUseCaseImpl(
    private val signupTokenVerifier: SignupTokenVerifier,
    private val registerUserService: RegisterUserService
) : RegisterUserUseCase {
    private val logger = LoggerFactory.getLogger(RegisterUserUseCaseImpl::class.java)

    override suspend fun handle(command: RegisterUserCommand): RegisterUserResult {
        try {
            val verificationResult = signupTokenVerifier.verifyTokenAndCreateUser(command.token, command.email)

            registerUserService.create(verificationResult.userId, command.username, command.nickname, command.profileImageUrl)

            logger.info("created user")

            return RegisterUserResult( otp = verificationResult.otp )
        } catch (e: InvalidSignupTokenException) {//authUser가 만들어지지 않았음이 확실한 경우
            throw e
        } catch (e: Exception) {//그외 : 보상
            registerUserService.recordCompensationTransaction(command.email )
           throw e
        }
    }
}