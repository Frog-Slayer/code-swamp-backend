package dev.codeswamp.core.article.application.service.acl

interface FolderAcl {
    fun getFolderIdByPath(path: String): Long?
}