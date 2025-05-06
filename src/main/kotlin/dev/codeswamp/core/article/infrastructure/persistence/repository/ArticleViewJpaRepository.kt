package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleView
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleViewJpaRepository : JpaRepository<ArticleView, Long> {
}