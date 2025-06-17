package dev.codeswamp.user.application.acl

interface FolderAcl {
    fun createRootFolder(userId: Long, username: String)
}