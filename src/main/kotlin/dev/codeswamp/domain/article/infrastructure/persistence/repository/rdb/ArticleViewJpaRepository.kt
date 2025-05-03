package dev.codeswamp.domain.article.infrastructure.persistence.repository.rdb

import dev.codeswamp.domain.article.infrastructure.persistence.entity.rdb.ArticleView
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleViewJpaRepository : JpaRepository<ArticleView, Long> {
}