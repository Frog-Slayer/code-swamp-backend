package dev.codeswamp.auth.infrastructure.grpc.server

import dev.codeswamp.auth.application.service.AuthApplicationService
import dev.codeswamp.auth.application.signup.TemporaryTokenService
import dev.codeswamp.grpc.AuthServiceGrpcKt
import dev.codeswamp.grpc.TokenAuthenticationRequest
import dev.codeswamp.grpc.UserIdResponse
import io.grpc.Status
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class SignupTokenVerificationServiceImpl (
    private val authApplicationService: AuthApplicationService
): AuthServiceGrpcKt.AuthServiceCoroutineImplBase() {

    override suspend fun verifyTokenAndCreateUser(request: TokenAuthenticationRequest): UserIdResponse {
        try {
            val result = authApplicationService.signup(request.email, request.signupToken)

            return UserIdResponse.newBuilder().setUserId(result).build()
        } catch (e: RuntimeException) {
             throw Status.INTERNAL
                .withDescription(e.message)
                .asRuntimeException()
        }
    }
}