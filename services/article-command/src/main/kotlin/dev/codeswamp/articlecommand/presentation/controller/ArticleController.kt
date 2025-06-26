package dev.codeswamp.articlecommand.presentation.controller

import dev.codeswamp.articlecommand.application.usecase.command.article.ArticleCommandUseCaseFacade
import dev.codeswamp.articlecommand.application.usecase.query.article.ArticleQueryUseCaseFacade
import dev.codeswamp.articlecommand.application.usecase.query.article.readversionedarticle.GetVersionedArticleQuery
import dev.codeswamp.articlecommand.presentation.dto.request.article.DraftRequest
import dev.codeswamp.articlecommand.presentation.dto.request.article.DraftUpdateRequest
import dev.codeswamp.articlecommand.presentation.dto.request.article.PublishRequest
import dev.codeswamp.articlecommand.presentation.dto.request.article.PublishUpdateRequest
import dev.codeswamp.articlecommand.presentation.dto.response.SimpleResponse
import dev.codeswamp.articlecommand.presentation.dto.response.article.ArticleReadResponse
import dev.codeswamp.articlecommand.presentation.dto.response.article.DraftResponse
import dev.codeswamp.articlecommand.presentation.dto.response.article.PublishResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val commandFacade: ArticleCommandUseCaseFacade,
    private val queryFacade: ArticleQueryUseCaseFacade
) {
    // TODO: ResponseEntity로 래핑 + 값 검증

    private val logger = LoggerFactory.getLogger(ArticleController::class.java)

    @GetMapping("/{articleId}/versions/{versionId}")
    suspend fun getVersionedArticle(
        @RequestAttribute userId: Long,
        @PathVariable articleId: Long,
        @PathVariable versionId: Long
    )
    : ArticleReadResponse {
        return ArticleReadResponse.Companion.from(
            queryFacade.getVersionedArticle(
                GetVersionedArticleQuery(
                    userId = userId,
                    articleId = articleId,
                    versionId = versionId
                )
            )
        )
    }

    @GetMapping("/drafts")
    suspend fun getDrafts(
        @RequestAttribute userId: Long,
    ){
        TODO("not implemented")
    }

    @PostMapping("/publish")
    suspend fun publishNew(
        @RequestAttribute userId: Long,
        @RequestBody publishRequest: PublishRequest,
    ): PublishResponse {
        return PublishResponse.Companion.from(commandFacade.createPublish(publishRequest.toCommand(userId)))
    }

    @PostMapping("/{articleId}/publish")
    suspend fun publish(
        @RequestAttribute userId: Long,
        @PathVariable articleId: Long,
        @RequestBody publishRequest: PublishUpdateRequest
    ): PublishResponse {
        return PublishResponse.Companion.from(commandFacade.updatePublish(publishRequest.toCommand(userId, articleId)))
    }

    @PostMapping("/draft")
    suspend fun draftNew(
        @RequestAttribute userId: Long,
        @RequestBody draftArticleRequest: DraftRequest
    ): DraftResponse {
        return DraftResponse.Companion.from(commandFacade.createDraft(draftArticleRequest.toCommand(userId)))
    }

    @PatchMapping("/{articleId}/versions/{versionId}/draft")
    suspend fun draft(
        @RequestAttribute userId: Long,
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @RequestBody draftArticleRequest: DraftUpdateRequest
    ): DraftResponse {
        return DraftResponse.Companion.from(commandFacade.updateDraft(draftArticleRequest.toCommand(userId, articleId, versionId)))
    }

    @PatchMapping("/{articleId}/versions/{versionId}/archive")
    suspend fun publishNew(
        @RequestAttribute userId: Long,
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
    ): SimpleResponse {

        return SimpleResponse("Archived article($articleId):version($versionId) successfully")
    }

    @DeleteMapping("/{articleId}")
    suspend fun delete(
        @RequestAttribute userId: Long,
        @PathVariable articleId: Long,
    ) : SimpleResponse {

        return SimpleResponse("Deleted article with id $articleId successfully")
    }
}