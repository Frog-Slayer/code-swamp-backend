package dev.codeswamp.core.article.presentation.controller

import dev.codeswamp.core.article.application.dto.command.CreateArticleCommand
import dev.codeswamp.core.article.application.dto.query.GetArticleByIdQuery
import dev.codeswamp.core.article.application.dto.query.GetArticleByPathQuery
import dev.codeswamp.core.article.application.dto.query.GetVersionedArticleQuery
import dev.codeswamp.core.article.application.usecase.ArticleCommandUseCase
import dev.codeswamp.core.article.application.usecase.ArticleQueryUseCase
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.presentation.dto.request.ArticleCreateRequestDto
import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
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
    private val articleCommandUsecase: ArticleCommandUseCase,
    private val articleQueryService: ArticleQueryUseCase
){
    //TODO(ResponseDTO & 예외 처리)

    @GetMapping("/{articleId}")
    fun getArticleWithId(@AuthenticationPrincipal user: CustomUserDetails, @PathVariable articleId:Long): VersionedArticle {
        return articleQueryService.findByArticleId(GetArticleByIdQuery(
            userId = user.getId(),
            articleId = articleId
        ))
    }

    @GetMapping("/@{username}/**")
    fun getArticleWithUsernameAndPath(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable username: String,
        httpServletRequest: HttpServletRequest
    ) : VersionedArticle {
        val fullPath = httpServletRequest.requestURI

        return articleQueryService.getArticleByUsernameAndPath(GetArticleByPathQuery(
            userId = user.getId(),
            path = fullPath
        ))
    }

    @GetMapping("/{articleId}/versions/{versionId}")
    fun getVersionedArticle(@AuthenticationPrincipal user: CustomUserDetails, @PathVariable articleId: Long, @PathVariable versionId: Long) : VersionedArticle{
        val userId = requireNotNull(user.getId())
        return articleQueryService.getVersionedArticle(
            GetVersionedArticleQuery(
                userId = userId,
                articleId = articleId,
                versionId = versionId
            )
        )
    }

    @PostMapping
    fun publishNewArticle(@AuthenticationPrincipal user: CustomUserDetails, @RequestBody articleCreateRequestDto: ArticleCreateRequestDto) {
        val userId = requireNotNull(user.getId())

        val createArticleCommand = CreateArticleCommand(
            userId = userId,
            title = articleCreateRequestDto.title,
            content = articleCreateRequestDto.content,
            isPublic = articleCreateRequestDto.isPublic,
            thumbnailUrl = articleCreateRequestDto.thumbnailUrl,
            slug = articleCreateRequestDto.slug,
            summary = articleCreateRequestDto.summary,
            folderId = 1L
        )

        articleCommandUsecase.create(createArticleCommand)
    }

    @PatchMapping("/{articleId}/versions/{versionId}")
    fun updateArticle (@AuthenticationPrincipal user: CustomUserDetails,
                       @PathVariable articleId: Long,
                       @PathVariable versionId: Long,
                       @RequestBody articleCreateRequestDto: ArticleCreateRequestDto) {

        val userId = requireNotNull(user.getId())

        val createArticleCommand = CreateArticleCommand(
            userId = userId,
            title = articleCreateRequestDto.title,
            content = articleCreateRequestDto.content,
            isPublic = articleCreateRequestDto.isPublic,
            thumbnailUrl = articleCreateRequestDto.thumbnailUrl,
            slug = articleCreateRequestDto.slug,
            summary = articleCreateRequestDto.summary,
            folderId = 1L
        )

        articleCommandUsecase.create(createArticleCommand)
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@AuthenticationPrincipal user: UserEntity, @PathVariable id: Long) {
        TODO("Not yet implemented")
    }
}