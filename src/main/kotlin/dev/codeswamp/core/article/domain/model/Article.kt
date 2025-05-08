package dev.codeswamp.core.article.domain.model

import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import java.time.Instant

data class Article (
    //metatdata
    val id: Long?,
    var title: String = "",
    var type: ArticleType = ArticleType.NEW,

    val userId: Long,
    var isPublic: Boolean = true,

    var createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now(),

    //content(raw Markdown document)
    var content: String,
) {
    fun changeTitle(title: String) {
        this.title = title
    }

    fun changeContent(content: String) {
        if (this.content != content) {
            this.content = content
            updateDate()
        }
    }

    fun publish() {
        if (this.type != ArticleType.DRAFT) {
            this.type = ArticleType.PUBLISHED
            createdAt = Instant.now()
        }
    }

    fun updateDate() {
        updatedAt = Instant.now()
    }
}
