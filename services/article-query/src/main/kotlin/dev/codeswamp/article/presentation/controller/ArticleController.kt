package dev.codeswamp.article.presentation.controller

import dev.codeswamp.article.application.usecase.query.article.ArticleQueryUseCaseFacade
import dev.codeswamp.article.application.usecase.query.article.read.byid.GetPublishedArticleByIdQuery
import dev.codeswamp.article.application.usecase.query.article.read.byslug.GetPublishedArticleBySlugQuery
import dev.codeswamp.article.application.usecase.query.article.read.withversion.GetVersionedArticleQuery
import dev.codeswamp.article.presentation.dto.request.article.DraftRequest
import dev.codeswamp.article.presentation.dto.request.article.DraftUpdateRequest
import dev.codeswamp.article.presentation.dto.request.article.PublishRequest
import dev.codeswamp.article.presentation.dto.request.article.PublishUpdateRequest
import dev.codeswamp.article.presentation.dto.response.article.ArticleReadResponse
import dev.codeswamp.article.presentation.dto.response.article.DraftResponse
import dev.codeswamp.article.presentation.dto.response.article.PublishResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder

@RestController
class ArticleController(
    private val queryFacade: ArticleQueryUseCaseFacade
) {
    private val logger = LoggerFactory.getLogger(ArticleController::class.java)

    @GetMapping("/{articleId}")
    fun getArticleWithId(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable articleId: Long
    )
            : ArticleReadResponse {
        return ArticleReadResponse.Companion.from(
            queryFacade.getPublishedArticleById(
                query = GetPublishedArticleByIdQuery(
                    userId = user.getId(),
                    articleId = articleId
                )
            )
        )
    }

    @GetMapping("/@{username}/**")
    fun getArticleByPathAndSlug(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable username: String,
        httpServletRequest: HttpServletRequest
    ): ArticleReadResponse {
        val rawPath = httpServletRequest.requestURI.removePrefix("/api/articles/")
        val fullPath = URLDecoder.decode(rawPath, "UTF-8")

        logger.info("full path: {}", fullPath)

        return ArticleReadResponse.Companion.from(
            queryFacade.getPublishedArticleBySlug(
                GetPublishedArticleBySlugQuery(
                    userId = user.getId(),
                    path = fullPath
                )
            )
        )
    }
}