package dev.codeswamp.articlecommand.domain.article.service

import dev.codeswamp.articlecommand.domain.article.exception.DuplicatedSlugException
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class SlugUniquenessChecker(
    private val articleRepository: ArticleRepository,
) {
    suspend fun checkSlugUniqueness(article: VersionedArticle, folderId: Long, slug: Slug) {
        val slugValue = slug.value

        val existingArticleId = articleRepository.findIdByFolderIdAndSlug(folderId, slugValue)
        if (existingArticleId != null && article.id != existingArticleId) throw DuplicatedSlugException(slugValue)
    }
}