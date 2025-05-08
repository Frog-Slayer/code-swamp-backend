package dev.codeswamp.core.article.infrastructure.persistence.entity

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
@Table(name = "article")
data class ArticleEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    val authorId: Long,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @CreatedDate
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    var updatedAt: Instant = Instant.now(),

    var isPublic: Boolean,

    val comments: MutableList<Long> = mutableListOf(),

    @Column(nullable = false)
    var folderId: Long,

    @OneToMany
    val views: MutableList<ArticleView> = mutableListOf(),
) {
    fun toArticle(): Article {
        return Article(
            id = id,
            title =  title,
            type = ArticleType.NEW,//TODO
            authorId = authorId,
            folderId = folderId,
            isPublic = isPublic,
            createdAt = createdAt,
            updatedAt = updatedAt,
            content = content
        )
    }
}