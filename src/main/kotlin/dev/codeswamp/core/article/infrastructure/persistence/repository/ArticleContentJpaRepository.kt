package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleContentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ArticleContentJpaRepository : JpaRepository<ArticleContentEntity, Long> {
    fun findAllByIdIsIn(ids: List<Long>): List<ArticleContentEntity>
}