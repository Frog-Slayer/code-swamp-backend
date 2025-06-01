package dev.codeswamp.core.article.domain.model

import org.springframework.security.access.AccessDeniedException
import java.time.Instant

data class Article (
    //metatdata
    val id: Long? = null,
    var type: ArticleType = ArticleType.NEW,

    var title: String = "",
    val authorId: Long,

    val summary: String,
    val thumbnailUrl: String? = null,

    var isPublic: Boolean = true,
    var content: String, //content(raw Markdown document)

    val slug: String,
    val folderId: Long,//TODO 의존성..?

    val createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now(),
    var currentVersion: Long? = null,

) {
    fun changeContent(content: String) {
        if (this.content != content) {
            this.content = content
            updateDate()
        }
    }

    fun updateDate() {
        updatedAt = Instant.now()
    }

    fun checkOwnership(userId: Long) {
        if (authorId != userId) throw AccessDeniedException("You are not the owner of this article")
    }

    fun assertReadableBy(userId: Long?) {
        if (!isPublic && (userId == null || authorId != userId))
            throw AccessDeniedException("cannot read private article")
    }

}
