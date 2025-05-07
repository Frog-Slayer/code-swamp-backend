package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ArticleMetadataJpaRepository : JpaRepository<ArticleMetadataEntity, Long> {

    override fun findById(id: Long): Optional<ArticleMetadataEntity?>
    fun findByIdIsIn(ids: List<Long>): List<ArticleMetadataEntity>

    fun save(articleMetadataEntity: ArticleMetadataEntity)
}