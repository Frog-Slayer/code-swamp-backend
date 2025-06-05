package dev.codeswamp.core.article.domain.article.repository

import dev.codeswamp.core.article.domain.article.model.VersionedArticle

interface ArticleRepository {
    fun save(versionedArticle: VersionedArticle): VersionedArticle
    fun deleteById(id: Long)

    fun findAllByIds(articleIds : List<Long>): List<VersionedArticle>
    fun findById(articleId : Long): VersionedArticle?
    fun findByFolderIdAndSlug(folderId: Long, slug: String): VersionedArticle?
}