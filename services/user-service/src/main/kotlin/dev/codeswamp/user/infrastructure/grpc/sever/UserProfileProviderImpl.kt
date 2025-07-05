package dev.codeswamp.user.infrastructure.grpc.sever

import dev.codeswamp.grpc.UserProfileRequest
import dev.codeswamp.grpc.UserProfileResponse
import dev.codeswamp.grpc.UserProfilesRequest
import dev.codeswamp.grpc.UserProfilesResponse
import dev.codeswamp.grpc.UserServiceGrpcKt
import dev.codeswamp.user.application.port.incoming.UserProfileProvider
import io.grpc.Status
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserProfileServiceImpl(
    private val userProfileProvider: UserProfileProvider
) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {

    override suspend fun fetchUserProfile(request: UserProfileRequest): UserProfileResponse {
        try {
            val profile = userProfileProvider.provideUserProfile(request.userId)

            return UserProfileResponse.newBuilder()
                .setUserId(profile.userId)
                .setUsername(profile.username)
                .setNickname(profile.nickname)
                .setProfileImage(profile.profileImage)
                .build()
        } catch (e: RuntimeException) {
            throw Status.NOT_FOUND
                .withDescription(e.message)
                .asRuntimeException()
        }
    }

    override suspend fun fetchUserProfiles(request: UserProfilesRequest): UserProfilesResponse {
        try {
            val profiles = userProfileProvider.provideUserProfiles(request.userIdList)

            val profileResponses = profiles.map { profile ->
                UserProfileResponse.newBuilder()
                    .setUserId(profile.userId)
                    .setUsername(profile.username)
                    .setNickname(profile.nickname)
                    .setProfileImage(profile.profileImage)
                    .build()
            }

            return UserProfilesResponse.newBuilder()
                .addAllProfiles(profileResponses)
               .build()
        } catch (e: RuntimeException) {
            throw Status.NOT_FOUND
                .withDescription(e.message)
                .asRuntimeException()
        }
    }
}