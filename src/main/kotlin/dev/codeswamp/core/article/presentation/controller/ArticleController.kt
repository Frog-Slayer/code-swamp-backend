package dev.codeswamp.core.article.presentation.controller

import dev.codeswamp.core.article.application.dto.command.ArticleWriteCommand
import dev.codeswamp.core.article.application.dto.query.GetVersionedArticleQuery
import dev.codeswamp.core.article.application.service.ArticleCommandService
import dev.codeswamp.core.article.application.service.ArticleQueryService
import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.presentation.dto.request.ArticleWriteRequestDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val articleCommandService: ArticleCommandService,
    private val articleQueryService: ArticleQueryService
){
    @GetMapping("/{id}")
    fun getArticleWithId(@AuthenticationPrincipal user: CustomUserDetails, @PathVariable id:Long): ArticleReadResponseDto? {
        val userId = user.getId()

        TODO()
    }

    @GetMapping("/@{username}/**")
    fun getArticleWithUsernameAndPath(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable username: String,
        httpServletRequest: HttpServletRequest
    ) {


    }


    @GetMapping("/{articleId}/versions/{versionId}")
    fun getVersionedArticle(@AuthenticationPrincipal user: CustomUserDetails, @PathVariable articleId: Long, @PathVariable versionId: Long) : Article{
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
    fun saveArticle(@AuthenticationPrincipal user: CustomUserDetails, @RequestBody articleWriteRequestDto: ArticleWriteRequestDto) {
        val userId = requireNotNull(user.getId())

        val articleWriteCommand = ArticleWriteCommand(
            userId = userId,
            title = articleWriteRequestDto.title,
            content = articleWriteRequestDto.content,
            isPublic = articleWriteRequestDto.isPublic,
            thumbnailUrl = articleWriteRequestDto.thumbnailUrl,
            slug = articleWriteRequestDto.slug,
            summary = articleWriteRequestDto.summary,
            folderId = 1L
        )

        articleCommandService.create(articleWriteCommand)
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@AuthenticationPrincipal user: UserEntity, @PathVariable id: Long) {
        TODO("Not yet implemented")
    }


}