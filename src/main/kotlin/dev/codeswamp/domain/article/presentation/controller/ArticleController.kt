package dev.codeswamp.domain.article.presentation.controller

import dev.codeswamp.domain.article.application.service.ArticleApplicationService
import dev.codeswamp.domain.article.domain.model.Article
import dev.codeswamp.domain.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.domain.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.domain.user.entity.User
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
    fun getArticle(@AuthenticationPrincipal user: User, @PathVariable id:Long): ArticleReadResponseDto? {
        TODO("Not yet implemented")
    }

    @PostMapping("/")
    fun saveArticle(@AuthenticationPrincipal user: User, @RequestBody article: Article): ArticleReadResponseDto? {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@AuthenticationPrincipal user: User, @PathVariable id: Long) {
        TODO("Not yet implemented")
    }

    @PatchMapping("/{id}")
    fun modifyArticleMetadata(@AuthenticationPrincipal user: User, @RequestBody articleMetadata: ArticleMetadataDto) {
        TODO("Not yet implemented")
    }

    @PutMapping("/{id}")
    fun modifyArticleContent(@AuthenticationPrincipal user: User, @PathVariable id: Long, @RequestBody content: String) {
        TODO("Not yet implemented")
    }
}