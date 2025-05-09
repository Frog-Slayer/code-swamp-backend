package dev.codeswamp.core.article.infrastructure.persistence

import dev.codeswamp.core.article.domain.model.ArticleDiff
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleDiffRepositoryImpl : ArticleDiffRepository {
    override fun save(articleDiff: ArticleDiff): ArticleDiff {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): ArticleDiff? {
        TODO("Not yet implemented")
    }

    override fun findByArticleId(articleId: Long): List<ArticleDiff> {
        TODO("Not yet implemented")
    }

    override fun findAllByIdsIn(diffs: List<Long>): List<ArticleDiff> {
        TODO("Not yet implemented")
    }

    override fun deleteByArticleId(articleId: Long) {
        TODO("Not yet implemented")
    }
}