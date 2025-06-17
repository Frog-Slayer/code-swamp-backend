package dev.codeswamp.user.infrastructure.grpc.client

import dev.codeswamp.grpc.CreateRootFolderRequest
import dev.codeswamp.grpc.FolderServiceGrpcKt
import dev.codeswamp.user.application.port.outgoing.RootFolderInitializer
import io.grpc.ManagedChannel
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

@Component
class GrpcRootFolderInitializerImpl : RootFolderInitializer {
    @GrpcClient("folder") lateinit var stub: FolderServiceGrpcKt.FolderServiceCoroutineStub

    override suspend fun createRootFolder(userId: Long, username: String): Boolean {
        val request = CreateRootFolderRequest.newBuilder()
            .setUserId(userId)
            .setUsername(username)
            .build()

        return stub.createRootFolder(request).success
    }
}