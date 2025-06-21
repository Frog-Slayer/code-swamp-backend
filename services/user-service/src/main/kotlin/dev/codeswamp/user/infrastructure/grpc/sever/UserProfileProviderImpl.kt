package dev.codeswamp.user.infrastructure.grpc.sever

import dev.codeswamp.grpc.UserProfileRequest
import dev.codeswamp.grpc.UserProfileResponse
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
                .setProfileImage(profile.profileImage)
                .setNickname(profile.nickname)
                .build()
        } catch (e: RuntimeException) {
            throw Status.NOT_FOUND
                .withDescription(e.message)
                .asRuntimeException()
        }

    }
}