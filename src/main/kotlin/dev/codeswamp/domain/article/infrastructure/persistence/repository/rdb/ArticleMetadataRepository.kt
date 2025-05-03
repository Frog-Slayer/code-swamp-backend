package dev.codeswamp.domain.article.infrastructure.persistence.repository.rdb

import dev.codeswamp.domain.article.infrastructure.persistence.entity.rdb.ArticleMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ArticleMetadataRepository : JpaRepository<ArticleMetadataEntity, Long> {

    override fun findById(id: Long): Optional<ArticleMetadataEntity?>
    fun findByIdIsIn(ids: List<Long>): List<ArticleMetadataEntity>

    fun save(articleMetadataEntity: ArticleMetadataEntity)
}