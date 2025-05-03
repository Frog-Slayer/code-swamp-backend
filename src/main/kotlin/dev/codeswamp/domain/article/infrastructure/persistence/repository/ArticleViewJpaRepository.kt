package dev.codeswamp.domain.article.infrastructure.persistence.repository

import dev.codeswamp.domain.article.infrastructure.persistence.entity.ArticleView
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleViewJpaRepository : JpaRepository<ArticleView, Long> {
}