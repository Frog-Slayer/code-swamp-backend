package dev.codeswamp.article.application.cache

import java.time.Instant

interface FolderDeletionCache {
    fun markAsDeleted(folders: List<Long>, deletedAt: Instant)
    fun isDeleted(folderId: Long): Boolean
    fun removeDeletedMark(folders: List<Long>)
}