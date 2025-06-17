package dev.codeswamp.auth.infrastructure.grpc.client

import dev.codeswamp.auth.application.dto.UserProfile
import dev.codeswamp.auth.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.grpc.UserProfileRequest
import dev.codeswamp.grpc.UserServiceGrpcKt
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

@Component
class GrpcUserProfileFetcherImpl : UserProfileFetcher {

    @GrpcClient("user-service") lateinit var stub: UserServiceGrpcKt.UserServiceCoroutineStub

    override suspend fun fetchUserProfile(userId: Long): UserProfile {
        try {
            val request = UserProfileRequest.newBuilder().setUserId(userId).build()

            val response = stub.fetchUserProfile(request)

            return UserProfile(
                response.nickname,
                response.profileImage
            )
        } catch (e: Exception) {
            throw RuntimeException("cannot fetch user profile")
        }
    }
}