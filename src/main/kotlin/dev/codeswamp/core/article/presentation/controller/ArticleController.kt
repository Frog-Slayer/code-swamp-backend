package dev.codeswamp.core.article.presentation.controller

import dev.codeswamp.core.article.application.service.ArticleApplicationService
import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.infrastructure.entity.UserEntity
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
    private val articleApplicationService: ArticleApplicationService
){
    @GetMapping("/{id}")
    fun getArticle(@AuthenticationPrincipal user: UserEntity, @PathVariable id:Long): ArticleReadResponseDto? {
        TODO("Not yet implemented")
    }

    @PostMapping("/")
    fun saveArticle(@AuthenticationPrincipal user: UserEntity, @RequestBody article: Article): ArticleReadResponseDto? {
        TODO("Not yet implemented")
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