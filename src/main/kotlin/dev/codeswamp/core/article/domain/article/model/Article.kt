package dev.codeswamp.core.article.domain.article.model

import dev.codeswamp.core.article.domain.support.IdGenerator
import org.springframework.security.access.AccessDeniedException
import java.time.Instant

data class Article (
    val id: Long = IdGenerator.generate(),
    val status: ArticleStatus,

    val title: String = "",
    val authorId: Long,

    val summary: String,
    val thumbnailUrl: String? = null,

    val isPublic: Boolean = true,
    val content: String, //content(raw Markdown document)

    val slug: String,
    val folderId: Long,//TODO 의존성..?

    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    val currentVersion: Long? = null,

    ) {

    fun checkOwnership(userId: Long) {
        if (authorId != userId) throw AccessDeniedException("You are not the owner of this article")
    }

    fun assertReadableBy(userId: Long?) {
        if (!isPublic && (userId == null || authorId != userId))
            throw AccessDeniedException("cannot read private article")
    }

}
