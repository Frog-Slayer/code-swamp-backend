package dev.codeswamp.article.presentation.controller

import dev.codeswamp.article.application.usecase.command.article.ArticleCommandUseCaseFacade
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
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URLDecoder

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val commandFacade: ArticleCommandUseCaseFacade,
    private val queryFacade: ArticleQueryUseCaseFacade
){
    private val logger = LoggerFactory.getLogger(ArticleController::class.java)

    @GetMapping("/{articleId}")
    fun getArticleWithId(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable articleId:Long)
    : ArticleReadResponse {
       return ArticleReadResponse.Companion.from( queryFacade.getPublishedArticleById(query = GetPublishedArticleByIdQuery(
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
    ) : ArticleReadResponse {
        val rawPath = httpServletRequest.requestURI.removePrefix("/api/articles/")
        val fullPath = URLDecoder.decode(rawPath, "UTF-8")

        logger.info("full path: {}", fullPath)

        return ArticleReadResponse.Companion.from( queryFacade.getPublishedArticleBySlug(
            GetPublishedArticleBySlugQuery(
                userId = user.getId(),
                path = fullPath
            )
        ))
    }

    @GetMapping("/{articleId}/versions/{versionId}")
    fun getVersionedArticle(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable articleId: Long,
        @PathVariable versionId: Long)
    : ArticleReadResponse {
        val userId = requireNotNull(user.getId())
        return ArticleReadResponse.Companion.from( queryFacade.getVersionedArticle(
            GetVersionedArticleQuery(
                userId = userId,
                articleId = articleId,
                versionId = versionId
            )
        ))
    }

    @PostMapping( "/publish")
    fun publishNew(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestBody publishRequest: PublishRequest,
    ) : PublishResponse {
        return PublishResponse.Companion.from(commandFacade.createPublish(publishRequest.toCommand(user.getId())))
    }

    @PostMapping("/{articleId}/publish")
    fun  publish(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable articleId: Long,
        @RequestBody publishRequest: PublishUpdateRequest
    ) : PublishResponse {
        return PublishResponse.Companion.from(commandFacade.updatePublish(publishRequest.toCommand(user.getId(), articleId)))
    }

    @PostMapping("/draft")
    fun draftNew(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestBody draftArticleRequest: DraftRequest
    ) : DraftResponse {
        return DraftResponse.Companion.from( commandFacade.createDraft(draftArticleRequest.toCommand(user.getId())))
    }

    @PatchMapping("/{articleId}/versions/{versionId}/draft")
    fun draft(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @RequestBody draftArticleRequest: DraftUpdateRequest
    ): DraftResponse {
        return DraftResponse.Companion.from(commandFacade.updateDraft(draftArticleRequest.toCommand(user.getId(), articleId, versionId)))
    }
}