package dev.codeswamp.articlequery.infrastructure.adapter.outgoing.grpc

import dev.codeswamp.articlequery.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.articlequery.application.readmodel.model.UserProfile
import dev.codeswamp.grpc.UserProfileRequest
import dev.codeswamp.grpc.UserProfilesRequest
import dev.codeswamp.grpc.UserServiceGrpcKt
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

@Component
class GrpcUserProfileFetcherImpl : UserProfileFetcher {

    @GrpcClient("user-service")
    lateinit var stub: UserServiceGrpcKt.UserServiceCoroutineStub
    override suspend fun fetchUserProfile(userId: Long): UserProfile {
        try {
            val request = UserProfileRequest.newBuilder().setUserId(userId).build()
            val profile = stub.fetchUserProfile(request)

            return UserProfile(
                profile.userId,
                profile.username,
                profile.nickname,
                profile.profileImage
            )
        } catch (e: Exception) {
            throw RuntimeException("cannot fetch user profile")
        }
    }

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