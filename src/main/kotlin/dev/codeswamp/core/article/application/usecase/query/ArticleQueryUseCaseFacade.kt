package dev.codeswamp.core.article.application.usecase.query

import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult
import dev.codeswamp.core.article.application.usecase.query.read.withversion.GetVersionedArticleQuery
import dev.codeswamp.core.article.application.usecase.query.read.withversion.GetVersionedArticleUseCase
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.folder.service.FolderDomainService
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseFacade(
    private val getVersionedArticleUseCase: GetVersionedArticleUseCase
) {
    fun getVersionedArticle (getVersionedArticleQuery: GetVersionedArticleQuery): ReadArticleResult{
        return getVersionedArticleUseCase.handle(getVersionedArticleQuery)
    }

}