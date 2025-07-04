package dev.codeswamp.user.infrastructure.grpc.client

import dev.codeswamp.grpc.AuthServiceGrpcKt
import dev.codeswamp.grpc.TokenAuthenticationRequest
import dev.codeswamp.user.application.port.outgoing.SignupTokenVerifier
import dev.codeswamp.user.application.port.outgoing.VerificationResult
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GrpcSignupTokenVerifierImpl(
) : SignupTokenVerifier {
    private val logger = LoggerFactory.getLogger(GrpcSignupTokenVerifierImpl::class.java)

    @GrpcClient("auth-service")
    private lateinit var stub: AuthServiceGrpcKt.AuthServiceCoroutineStub

    override suspend fun verifyTokenAndCreateUser(signupToken: String, email: String): VerificationResult {
        try {

            val request = TokenAuthenticationRequest.newBuilder()
                .setEmail(email)
                .setSignupToken(signupToken)
                .build()

            val response = stub.verifyTokenAndCreateUser(request)

            return VerificationResult(
                response.userId,
                response.otp
            )
        } catch (e: Exception) {
            logger.warn("Verify token failed", e)
            throw e
        }
    }
}