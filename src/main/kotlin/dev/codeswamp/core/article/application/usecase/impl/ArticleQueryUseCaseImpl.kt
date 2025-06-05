package dev.codeswamp.core.article.application.usecase.impl

import dev.codeswamp.core.article.application.dto.query.GetArticleByIdQuery
import dev.codeswamp.core.article.application.dto.query.GetArticleByPathQuery
import dev.codeswamp.core.article.application.dto.query.GetVersionedArticleQuery
import dev.codeswamp.core.article.application.usecase.ArticleQueryUseCase
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.folder.service.FolderDomainService
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseImpl(
    private val articleDomainService: ArticleDomainService,
    private val versionService: VersionService,
    private val folderDomainService: FolderDomainService,
): ArticleQueryUseCase {

    override fun findByArticleId(getArticleByIdQuery: GetArticleByIdQuery): VersionedArticle {
        val article = requireNotNull(articleDomainService.findById(getArticleByIdQuery.articleId))

        article.assertReadableBy(getArticleByIdQuery.userId)

        return article
    }

    override fun findAllByKeywords(
        userId: Long?,
        keywords: List<String>
    ): List<ArticleReadResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getVersionedArticle (getVersionedArticleQuery: GetVersionedArticleQuery): VersionedArticle {
        val article = requireNotNull(articleDomainService.findById(getVersionedArticleQuery.articleId))

        article.checkOwnership(getVersionedArticleQuery.userId)

        val articleWithVersion = versionService.getRollbackedArticle(article, getVersionedArticleQuery.versionId)

        return articleWithVersion
    }

    override fun getArticleByUsernameAndPath(getArticleByPathQuery: GetArticleByPathQuery): VersionedArticle {
        val path = getArticleByPathQuery.path
        val lastSlash = path.lastIndexOf('/');

        val folderPath = path.substring(0, lastSlash)
        val articleSlug =  path.substring(lastSlash + 1)

        val folder = requireNotNull(folderDomainService.getFolderIdByPath(folderPath))

        val article = requireNotNull(articleDomainService.findByFolderIdAndSlug(folderId, articleSlug))

        article.assertReadableBy(getArticleByPathQuery.userId)

        return article;
    }



}