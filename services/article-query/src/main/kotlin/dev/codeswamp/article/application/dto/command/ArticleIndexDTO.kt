package dev.codeswamp.article.application.dto.command

import dev.codeswamp.article.domain.article.model.VersionedArticle

data class ArticleIndexDTO(
    val articleId: Long,
    val authorId: Long,
    val title: String,
    val preprocessedText: String,
    val isPublic: Boolean,
) {
    companion object {
        fun from(versionedArticle: VersionedArticle, preprocessedText: String) : ArticleIndexDTO {
            return ArticleIndexDTO(
                articleId = versionedArticle.id,
                authorId = versionedArticle.authorId,
                title = requireNotNull(versionedArticle.currentVersion.title).value,
                preprocessedText = preprocessedText,
                isPublic = versionedArticle.metadata.isPublic,
            )
        }
    }
}