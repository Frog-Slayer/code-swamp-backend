package dev.codeswamp.core.article.domain.folder.model

import org.springframework.security.access.AccessDeniedException

data class Folder(
    val id: Long? = null,
    val ownerId: Long,//UserId?
    var name: String,
    var parentId: Long? = null,//root = null -> 객체로?
) {
    init {
        validateName(name)
    }

    fun checkOwnership(userId: Long) {
        if (ownerId != userId) throw AccessDeniedException("You are not the owner of this folder")
    }

    fun validateName(name: String) {
        if (parentId == null) {//root
            require(name.startsWith("@")) { "Root folder name must start with '@'" }
            require(name.length in 2..30) { "Root folder name must be between 2 and 30 characters." }
            require(name.matches(Regex("^@[a-zA-Z0-9가-힣_\\-]+$"))) { "Root folder name contains invalid characters." }
        } else {//
            require(name.length in 1..31) { "Folder name must be between 1 and 31 characters." }
            require(name.matches(Regex("^[a-zA-Z0-9가-힣_\\- ]+$"))) { "Folder name contains invalid characters." }
        }
    }

}