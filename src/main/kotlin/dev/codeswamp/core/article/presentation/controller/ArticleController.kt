package dev.codeswamp.core.article.presentation.controller

import dev.codeswamp.core.article.application.usecase.command.ArticleCommandUseCaseFacade
import dev.codeswamp.core.article.application.usecase.command.draft.DraftArticleResult
import dev.codeswamp.core.article.application.usecase.command.publish.CreatePublishCommand
import dev.codeswamp.core.article.application.usecase.command.publish.PublishArticleResult
import dev.codeswamp.core.article.application.usecase.command.publish.UpdatePublishCommand
import dev.codeswamp.core.article.application.usecase.query.ArticleQueryUseCaseFacade
import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult
import dev.codeswamp.core.article.application.usecase.query.read.byid.GetPublishedArticleByIdQuery
import dev.codeswamp.core.article.application.usecase.query.read.byslug.GetPublishedArticleBySlugQuery
import dev.codeswamp.core.article.application.usecase.query.read.withversion.GetVersionedArticleQuery
import dev.codeswamp.core.article.presentation.dto.request.DraftRequest
import dev.codeswamp.core.article.presentation.dto.request.DraftUpdateRequest
import dev.codeswamp.core.article.presentation.dto.request.PublishRequest
import dev.codeswamp.core.article.presentation.dto.request.PublishUpdateRequest
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val commandFacade: ArticleCommandUseCaseFacade,
    private val queryFacade: ArticleQueryUseCaseFacade
){
    //TODO(ResponseDTO & 예외 처리)

    @GetMapping("/{articleId}")
    fun getArticleWithId(@AuthenticationPrincipal user: CustomUserDetails, @PathVariable articleId:Long): ReadArticleResult {
       return queryFacade.getPublishedArticleById(query = GetPublishedArticleByIdQuery(
           userId = user.getId(),
           articleId = articleId
       ))
    }

    @GetMapping("/@{username}/**")
    fun getArticleWithUsernameAndPath(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable username: String,
        httpServletRequest: HttpServletRequest
    ) : ReadArticleResult {
        val fullPath = httpServletRequest.requestURI

        return queryFacade.getPublishedArticleBySlug(GetPublishedArticleBySlugQuery(
            userId = user.getId(),
            path = fullPath
        ))
    }

    @GetMapping("/{articleId}/versions/{versionId}")
    fun getVersionedArticle(@AuthenticationPrincipal user: CustomUserDetails, @PathVariable articleId: Long, @PathVariable versionId: Long) :
    ReadArticleResult{
        val userId = requireNotNull(user.getId())
        return queryFacade.getVersionedArticle(GetVersionedArticleQuery(
                userId = userId,
                articleId = articleId,
                versionId = versionId
            )
        )
    }

    @PostMapping( "/publish")
    fun publishNew(@AuthenticationPrincipal user: CustomUserDetails,
                   @RequestBody publishRequest: PublishRequest,
    ) : PublishArticleResult {
        return commandFacade.createPublish(publishRequest.toCommand(user.getId()))
    }

    @PostMapping("/{articleId}/publish")
    fun  publish(@AuthenticationPrincipal user: CustomUserDetails,
                 @PathVariable articleId: Long,
                 @RequestBody publishRequest: PublishUpdateRequest
    ) : PublishArticleResult {
        return commandFacade.updatePublish(publishRequest.toCommand(user.getId(), articleId))
    }

    @PostMapping("/draft")
    fun draftNew(@AuthenticationPrincipal user: CustomUserDetails,
                 @RequestBody draftArticleRequest: DraftRequest
    ) : DraftArticleResult {
        return commandFacade.createDraft(draftArticleRequest.toCommand(user.getId()))
    }

    @PatchMapping("/{articleId}/versions/{versionId}")
    fun draft(@AuthenticationPrincipal user: CustomUserDetails,
              @PathVariable articleId: Long,
              @PathVariable versionId: Long,
              @RequestBody draftArticleRequest: DraftUpdateRequest
    ): DraftArticleResult {
        return commandFacade.updateDraft(draftArticleRequest.toCommand(user.getId(), articleId, versionId))
    }
}