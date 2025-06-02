package dev.codeswamp.core.article.application.service.impl

import dev.codeswamp.core.article.application.dto.query.GetArticleByIdQuery
import dev.codeswamp.core.article.application.dto.query.GetArticleByPathQuery
import dev.codeswamp.core.article.application.dto.query.GetVersionedArticleQuery
import dev.codeswamp.core.article.application.service.ArticleQueryService
import dev.codeswamp.core.article.application.service.acl.FolderAcl
import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.service.ArticleHistoryService
import dev.codeswamp.core.article.domain.service.ArticleService
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import org.springframework.stereotype.Service

@Service
class ArticleQueryServiceImpl(
    private val articleService: ArticleService,
    private val articleHistoryService: ArticleHistoryService,
    private val folderAcl: FolderAcl
): ArticleQueryService {

    override fun findByArticleId(getArticleByIdQuery: GetArticleByIdQuery): Article {
        val article = requireNotNull(articleService.findById(getArticleByIdQuery.articleId))

        article.assertReadableBy(getArticleByIdQuery.userId)

        return article
    }

    override fun findAllByKeywords(
        userId: Long?,
        keywords: List<String>
    ): List<ArticleReadResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getVersionedArticle (getVersionedArticleQuery: GetVersionedArticleQuery): Article {
        val article = requireNotNull(articleService.findById(getVersionedArticleQuery.articleId))

        article.checkOwnership(getVersionedArticleQuery.userId)

        val articleWithVersion = articleHistoryService.getRollbackedArticle(article, getVersionedArticleQuery.versionId)

        return articleWithVersion
    }

    override fun getArticleByUsernameAndPath(getArticleByPathQuery: GetArticleByPathQuery): Article {
        val path = getArticleByPathQuery.path
        val lastSlash = path.lastIndexOf('/');

        val folderPath = path.substring(0, lastSlash)
        val articleSlug =  path.substring(lastSlash + 1)

        val folderId = requireNotNull(folderAcl.getFolderIdByPath(folderPath))

        val article = requireNotNull(articleService.findByFolderIdAndSlug(folderId, articleSlug))

        article.assertReadableBy(getArticleByPathQuery.userId)

        return article;
    }



}