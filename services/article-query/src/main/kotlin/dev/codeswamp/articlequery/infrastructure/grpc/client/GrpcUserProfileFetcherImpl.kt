package dev.codeswamp.articlequery.infrastructure.grpc.client

import dev.codeswamp.articlequery.application.dto.UserProfile
import dev.codeswamp.articlequery.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.grpc.UserProfileRequest
import dev.codeswamp.grpc.UserProfilesRequest
import dev.codeswamp.grpc.UserServiceGrpcKt
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

@Component
class GrpcUserProfileFetcherImpl : UserProfileFetcher {

    @GrpcClient("user-service")
    lateinit var stub: UserServiceGrpcKt.UserServiceCoroutineStub

    override suspend fun fetchUserProfiles(userIds: List<Long>): List<UserProfile> {
        try {
            val request = UserProfilesRequest.newBuilder().addAllUserId(userIds).build()
            val profiles = stub.fetchUserProfiles(request).profilesList

            return profiles.map { profile ->
                UserProfile(
                profile.userId,
                profile.username,
                profile.nickname,
                profile.profileImage
                )
            }
        } catch (e: Exception) {
            throw RuntimeException("cannot fetch user profile")
        }
    }
}