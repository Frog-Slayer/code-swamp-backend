package dev.codeswamp.user.application.port.outgoing

interface RootFolderInitializer {
    fun createRootFolder(userId: Long, username: String) : Boolean
}