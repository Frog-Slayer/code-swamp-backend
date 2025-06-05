package dev.codeswamp.core.article.application.usecase.query

import dev.codeswamp.core.article.application.dto.query.GetArticleByIdQuery
import dev.codeswamp.core.article.application.dto.query.GetArticleByPathQuery
import dev.codeswamp.core.article.application.dto.query.GetVersionedArticleQuery
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.folder.service.FolderDomainService
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseFacade(
    private val folderDomainService: FolderDomainService,
) {

    fun findByArticleId(getArticleByIdQuery: GetArticleByIdQuery): VersionedArticle {
        val article = requireNotNull(articleDomainService.findById(getArticleByIdQuery.articleId))

        article.assertReadableBy(getArticleByIdQuery.userId)

        return article
    }

    fun findAllByKeywords(
        userId: Long?,
        keywords: List<String>
    ): List<ArticleReadResponseDto> {
        TODO("Not yet implemented")
    }

    fun getVersionedArticle (getVersionedArticleQuery: GetVersionedArticleQuery): VersionedArticle {

    }

    fun getArticleByUsernameAndPath(getArticleByPathQuery: GetArticleByPathQuery): VersionedArticle {

    }
}