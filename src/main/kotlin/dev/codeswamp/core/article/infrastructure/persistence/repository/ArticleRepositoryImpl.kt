package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleType
import dev.codeswamp.core.article.domain.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleContentEntity
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleDiffEntity
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleEntity
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleMetadataEntity
import dev.codeswamp.core.article.infrastructure.utils.ArticleDiffUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    private val articleJpaRepository: ArticleJpaRepository,
    private val articleDiffJpaRepository: ArticleDiffJpaRepository,
) : ArticleRepository {

    @Transactional
    override fun save(article: Article): Article {
        val existingArticleEntity = article.id?.let { articleJpaRepository.findById(it).orElse(null) }

        val articleEntity = ArticleEntity(
            id = article.id,
            title = article.title,
            authorId = article.authorId,
            content = article.content,
            createdAt = article.createdAt,
            updatedAt = article.updatedAt,
            isPublic = article.isPublic,
            views = existingArticleEntity?.views ?: mutableListOf(),
            comments = existingArticleEntity?.comments ?: mutableListOf(),
            folderId = article.folderId,
        )

        val savedEntity = articleJpaRepository.save(articleEntity)

        //diff 확인 및 저장
        val diff : String? = ArticleDiffUtil.generateDiff(savedEntity.content, article.content)

        return if (diff == null) {
            savedEntity.toDomain()
        }
        else {
            val latestVersion = articleDiffJpaRepository.findLatestVersionByArticleId(savedEntity.id!!)
            val history = articleDiffJpaRepository.save (
                ArticleDiffEntity(
                    id = null,
                    article = savedEntity,
                    version = latestVersion + 1,
                    diffData = diff,
                    createdAt = article.createdAt
                )
            )

            savedEntity.currentVersion = history.version
            savedEntity.toDomain()
        }
    }

    override fun delete(article: Article) {
        TODO("Not yet implemented")
    }

    override fun findAllByIds(articleIds: List<Long>): List<Article> {
        TODO("Not yet implemented")
    }

    override fun findById(articleId: Long): Article? {
        TODO("Not yet implemented")
    }
}