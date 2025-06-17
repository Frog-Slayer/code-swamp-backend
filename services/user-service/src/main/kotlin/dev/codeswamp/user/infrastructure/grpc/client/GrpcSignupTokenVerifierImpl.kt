package dev.codeswamp.user.infrastructure.grpc.client

import dev.codeswamp.grpc.AuthServiceGrpcKt
import dev.codeswamp.grpc.CreateRootFolderRequest
import dev.codeswamp.grpc.FolderServiceGrpcKt
import dev.codeswamp.grpc.TokenAuthenticationRequest
import dev.codeswamp.grpc.TokenAuthenticationRequestKt
import dev.codeswamp.user.application.port.outgoing.RootFolderInitializer
import dev.codeswamp.user.application.port.outgoing.SignupTokenVerifier
import io.grpc.ManagedChannel
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

@Component
class GrpcSignupTokenVerifierImpl : SignupTokenVerifier {

    @GrpcClient("auth-service") lateinit var stub: AuthServiceGrpcKt.AuthServiceCoroutineStub

    override suspend fun verifyTokenAndCreateUser(signupToken: String, email: String): Long {
        try {
            val request = TokenAuthenticationRequest.newBuilder()
                .setEmail(email)
                .setSignupToken(signupToken)
                .build()

            return stub.verifyTokenAndCreateUser(request).userId
        } catch (e: Exception) {
            throw e
        }
    }
}