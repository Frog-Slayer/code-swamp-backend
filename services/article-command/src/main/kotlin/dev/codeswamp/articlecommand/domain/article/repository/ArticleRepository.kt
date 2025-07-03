package dev.codeswamp.articlecommand.domain.article.repository

import dev.codeswamp.articlecommand.domain.article.model.Article
import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionState

interface ArticleRepository {
    suspend fun findById(id: Long): Article?

    suspend fun createMetadata(article: Article)
    suspend fun updateMetadata(article: Article)

    suspend fun insertVersions(versions: List<Version>)
    suspend fun updateVersions(versions: List<Version>)

    suspend fun findByAuthorIdAndState(authorId: Long, state: VersionState): List<Version>
    suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?

    suspend fun deleteAllByFolderIdIn(folderIds: List<Long>)
    suspend fun deleteById(id: Long)
}