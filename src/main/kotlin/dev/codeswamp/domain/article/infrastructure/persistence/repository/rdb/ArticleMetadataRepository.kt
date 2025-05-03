package dev.codeswamp.domain.article.infrastructure.persistence.repository.rdb

import dev.codeswamp.domain.article.infrastructure.persistence.entity.rdb.ArticleMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleMetadataRepository : JpaRepository<ArticleMetadataEntity, Long> {
}