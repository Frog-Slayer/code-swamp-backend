package dev.codeswamp.core.article.infrastructure.persistence

import dev.codeswamp.core.article.domain.model.ArticleDiff
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleDiffEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.ArticleDiffJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.ArticleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleDiffRepositoryImpl(
    private val diffJpaRepository: ArticleDiffJpaRepository,
    private val articleJpaRepository: ArticleJpaRepository
) : ArticleDiffRepository {

    override fun save(articleDiff: ArticleDiff): ArticleDiff {
        return diffJpaRepository.save(
            ArticleDiffEntity(
                article = articleJpaRepository.findById(articleDiff.articleId).orElse(null),
                previousVersion = articleDiff.previousVersionId?.let {
                    diffJpaRepository.findById(it).orElse(null)
                },
                diffData = articleDiff.diffData,
                createdAt = articleDiff.createdAt,
            )
        ).toDomain()
    }

    override fun findById(id: Long): ArticleDiff? {
        return diffJpaRepository.findById(id).orElse(null).toDomain()
    }

    override fun findByArticleId(articleId: Long): List<ArticleDiff> {
        return diffJpaRepository.findAllByArticleId(articleId).map { it.toDomain() }
    }

    override fun findAllByIdsIn(diffs: List<Long>): List<ArticleDiff> {
        return diffJpaRepository.findAllByIdIsIn(diffs).map { it.toDomain() }
    }

    override fun deleteByArticleId(articleId: Long) {
        diffJpaRepository.deleteAllByArticleId(articleId)
    }
}