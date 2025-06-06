package dev.codeswamp.core.article.domain.article.repository

import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.Slug

interface ArticleRepository {
    fun save(versionedArticle: VersionedArticle): VersionedArticle
    fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
    fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle?
    fun countVersionsOfArticle(articleId: Long): Long
    fun saveVersion(version: Version): Version;
    fun deleteById(id: Long)
}