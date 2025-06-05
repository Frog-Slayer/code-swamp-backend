package dev.codeswamp.core.article.domain.article.repository

import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.VersionEntity

interface ArticleRepository {
    fun save(versionedArticle: VersionedArticle): VersionedArticle
    fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle?
    fun findByFolderIdAndSlug(folderId: Long, slug: String): VersionedArticle?
    fun saveVersion(version: Version): VersionEntity;
    fun deleteById(id: Long)
}