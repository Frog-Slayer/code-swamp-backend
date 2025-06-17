package dev.codeswamp.user.application.port.outgoing

interface RootFolderInitializer {
    suspend fun createRootFolder(userId: Long, username: String) : Boolean
}