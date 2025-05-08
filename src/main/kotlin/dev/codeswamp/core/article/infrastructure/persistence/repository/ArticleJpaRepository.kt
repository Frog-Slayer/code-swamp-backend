package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleJpaRepository : JpaRepository<ArticleEntity, Long>{
}