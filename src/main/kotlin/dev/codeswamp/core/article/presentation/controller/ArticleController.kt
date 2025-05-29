package dev.codeswamp.core.article.presentation.controller

import com.nimbusds.jose.shaded.gson.Gson
import dev.codeswamp.core.article.application.dto.command.ArticleWriteCommand
import dev.codeswamp.core.article.application.service.ArticleCommandService
import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.request.ArticleWriteRequestDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val articleCommandService: ArticleCommandService
){
    private val logger = LoggerFactory.getLogger(ArticleController::class.java)


    @GetMapping("/{id}")
    fun getArticle(@AuthenticationPrincipal user: UserEntity, @PathVariable id:Long): ArticleReadResponseDto? {
        TODO("Not yet implemented")
    }

    @PostMapping
    fun saveArticle(@AuthenticationPrincipal user: CustomUserDetails?, @RequestBody articleWriteRequestDto: ArticleWriteRequestDto) {
        logger.info(Gson().toJson(user))
        logger.info("Saving article")
        val userId = requireNotNull(user?.getId())

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

    @PatchMapping("/{id}")
    fun modifyArticleMetadata(@AuthenticationPrincipal user: UserEntity, @RequestBody articleMetadata: ArticleMetadataDto) {
        TODO("Not yet implemented")
    }

    @PutMapping("/{id}")
    fun modifyArticleContent(@AuthenticationPrincipal user: UserEntity, @PathVariable id: Long, @RequestBody content: String) {
        TODO("Not yet implemented")
    }
}