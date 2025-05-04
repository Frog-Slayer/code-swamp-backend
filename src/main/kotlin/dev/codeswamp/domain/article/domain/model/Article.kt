package dev.codeswamp.domain.article.domain.model

import dev.codeswamp.domain.folder.domain.entity.Folder
import dev.codeswamp.domain.user.entity.User
import java.time.Instant

data class Article (
    //metatdata
    val id: Long,
    var title: String,
    var type: ArticleType = ArticleType.NEW,

    val writer: User,
    var isPublic: Boolean,

    var createdAt: Instant,
    var updatedAt: Instant,

    //content(raw Markdown document)
    var content: String,
) {
    fun changeTitle(title: String) {
        this.title = title
    }

    fun changeContent(content: String) {
        this.content = content
        updateDate()
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
