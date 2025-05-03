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
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleApplicationService: ArticleApplicationService
){
    // 글 가져오기        GET /@{사용자명}/{slug}
    @GetMapping("/@{username}/{slug}")
    fun getArticle(@AuthenticationPrincipal user: User, @PathVariable username: String, @PathVariable slug: String): ArticleReadResponseDto? {
        TODO("Not yet implemented")
    }

    // 글 발행           POST /articles
    @PostMapping("/articles")
    fun saveArticle(@AuthenticationPrincipal user: User, @RequestBody article: Article): Article {
        TODO("Not yet implemented")
    }

    // 문서 삭제         DELETE /articles/{id}
    @DeleteMapping("/articles/{id}")
    fun deleteArticle(@AuthenticationPrincipal user: User, @PathVariable id: Long) {
        TODO("Not yet implemented")
    }

    // 메타 정보 수정     PATCH /articles/{id}
    @PatchMapping("/articles/{id}")
    fun modifyArticleMetadata(@AuthenticationPrincipal user: User, @RequestBody articleMetadata: ArticleMetadataDto) {
        TODO("Not yet implemented")
    }

    // 본문 수정         PUT /articles/{id}
    @PutMapping("/articles/{id}")
    fun modifyArticleContent(@AuthenticationPrincipal user: User, @PathVariable id: Long, @RequestBody content: String) {
        TODO("Not yet implemented")
    }
}