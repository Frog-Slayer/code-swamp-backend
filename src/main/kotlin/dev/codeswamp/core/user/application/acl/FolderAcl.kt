package dev.codeswamp.core.user.application.acl

interface FolderAcl {
    fun createRootFolder(userId: Long, username: String)
}