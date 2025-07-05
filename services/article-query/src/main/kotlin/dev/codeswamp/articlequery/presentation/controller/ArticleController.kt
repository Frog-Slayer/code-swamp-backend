package dev.codeswamp.articlequery.presentation.controller

import dev.codeswamp.articlequery.application.usecase.query.article.ArticleQueryUseCaseFacade
import dev.codeswamp.articlequery.application.usecase.query.article.list.recent.GetRecentArticlesQuery
import dev.codeswamp.articlequery.application.usecase.query.article.read.byid.GetPublishedArticleByIdQuery
import dev.codeswamp.articlequery.application.usecase.query.article.read.byslug.GetPublishedArticleBySlugQuery
import dev.codeswamp.articlequery.application.usecase.query.article.status.CheckVersionExistsQuery
import dev.codeswamp.articlequery.presentation.dto.response.article.ArticleCardResponseDto
import dev.codeswamp.articlequery.presentation.dto.response.article.ArticleReadResponse
import dev.codeswamp.articlequery.presentation.dto.response.article.CheckVersionExistsResponse
import dev.codeswamp.authcommon.security.CustomUserDetails
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder
import java.time.Instant

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val queryFacade: ArticleQueryUseCaseFacade,
    private val articleQueryUseCaseFacade: ArticleQueryUseCaseFacade
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

   @GetMapping("/{articleId}/versions/{versionId}/status")
   suspend fun checkVersionExists(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long
    ): ResponseEntity<CheckVersionExistsResponse> {
       val result = articleQueryUseCaseFacade.checkVersionExists(CheckVersionExistsQuery(
           articleId, versionId
       ))

       return ResponseEntity.ok(CheckVersionExistsResponse( result.fullPath))
    }

   @GetMapping("/recent")
   suspend fun getRecentArticles(
        @AuthenticationPrincipal user: CustomUserDetails?,
        @RequestParam limit: Int,
        @RequestParam(required = false) lastArticleId: Long?,
        @RequestParam(required = false) lastCreatedAt: Instant?
   ) : ResponseEntity<List<ArticleCardResponseDto>> {
       val query = GetRecentArticlesQuery(
           userId = user?.getId(),
           lastArticleId = lastArticleId,
           lastCreatedAt = lastCreatedAt,
           limit = limit
       )

       val articles = queryFacade.getRecentArticles(query)

       return ResponseEntity.ok(articles.map{ ArticleCardResponseDto.from(it) })
   }
}