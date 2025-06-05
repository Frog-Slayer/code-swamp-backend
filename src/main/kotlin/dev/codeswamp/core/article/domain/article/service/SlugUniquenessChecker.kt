package dev.codeswamp.core.article.domain.article.service

import dev.codeswamp.core.article.domain.article.exceptions.DuplicatedSlugException
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class SlugUniquenessChecker(
    private val articleRepository: ArticleRepository,
) {
    fun checkSlugUniqueness(article: VersionedArticle, folderId: Long, slug: Slug) {
        val slugValue = requireNotNull(slug.value)
        articleRepository.findByFolderIdAndSlug(folderId, slugValue)
            ?.takeIf { it.id != article.id }
            ?.let { throw DuplicatedSlugException("중복 SLUG입니다") }
    }

}