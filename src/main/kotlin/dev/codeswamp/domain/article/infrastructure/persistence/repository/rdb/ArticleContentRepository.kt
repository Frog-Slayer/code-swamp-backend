package dev.codeswamp.domain.article.infrastructure.persistence.repository.rdb

import dev.codeswamp.domain.article.infrastructure.persistence.entity.rdb.ArticleContentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ArticleContentRepository : JpaRepository<ArticleContentEntity, Long> {

    override fun findById(id: Long): Optional<ArticleContentEntity?>
    fun findAllByIdIsIn(ids: List<Long>): List<ArticleContentEntity>

    fun save(articleContent: ArticleContentEntity)
}