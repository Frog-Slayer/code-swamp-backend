package dev.codeswamp.core.article.infrastructure.persistence.mapper

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleContentEntity
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleMetadataEntity

object ArticleMapper {

    fun toDomain(articleMetadataEntity: ArticleMetadataEntity, articleContentEntity: ArticleContentEntity): Article {
        TODO("not implemented")
    }

    fun toMetadataEntity(article: Article): ArticleMetadataEntity {
        return ArticleMetadataEntity(

        )
    }

    fun toContentEntity(article: Article): ArticleContentEntity {
        TODO("not implemented")
    }

}