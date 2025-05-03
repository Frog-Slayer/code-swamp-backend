package dev.codeswamp.domain.article.infrastructure.persistence.repository

import dev.codeswamp.domain.article.infrastructure.persistence.entity.ArticleContentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ArticleContentJpaRepository : JpaRepository<ArticleContentEntity, Long> {

    override fun findById(id: Long): Optional<ArticleContentEntity?>
    fun findAllByIdIsIn(ids: List<Long>): List<ArticleContentEntity>

    fun save(articleContent: ArticleContentEntity)
}