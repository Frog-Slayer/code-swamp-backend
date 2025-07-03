package dev.codeswamp.articlequery.presentation.controller

import dev.codeswamp.articlequery.application.usecase.query.article.ArticleQueryUseCaseFacade
import dev.codeswamp.articlequery.application.usecase.query.article.read.byid.GetPublishedArticleByIdQuery
import dev.codeswamp.articlequery.application.usecase.query.article.read.byslug.GetPublishedArticleBySlugQuery
import dev.codeswamp.articlequery.presentation.dto.response.article.ArticleReadResponse
import dev.codeswamp.authcommon.security.CustomUserDetails
import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val queryFacade: ArticleQueryUseCaseFacade
) {
    private val logger = LoggerFactory.getLogger(ArticleController::class.java)

    @GetMapping("/{articleId}")
    suspend fun getArticleWithId(
        @AuthenticationPrincipal user: CustomUserDetails?,
        @PathVariable articleId: Long
    ): ArticleReadResponse {
        return ArticleReadResponse.Companion.from(
            queryFacade.getPublishedArticleById(
                query = GetPublishedArticleByIdQuery(
                    userId = user?.getId(),
                    articleId = articleId
                )
            )
        )
    }

    @GetMapping("/@{username}/**")
   suspend fun getArticleByPathAndSlug(
        @AuthenticationPrincipal user: CustomUserDetails?,
        @PathVariable username: String,
        request: ServerHttpRequest,
    ): ArticleReadResponse {
        val rawPath = request.uri.path.removePrefix("/articles/")
        val fullPath = URLDecoder.decode(rawPath, "UTF-8")

        logger.info("full path: {}", fullPath)

        return ArticleReadResponse.Companion.from(
            queryFacade.getPublishedArticleBySlug(
                GetPublishedArticleBySlugQuery(
                    userId = user?.getId(),
                    path = fullPath
                )
            )
        )
    }
}