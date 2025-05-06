package dev.codeswamp.core.comment.entity

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleMetadataEntity
import dev.codeswamp.core.user.infrastructure.entity.UserEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
data class Comment(

    @Id
    @GeneratedValue
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    val author: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_metadata_id")
    var articleMetadataEntity: ArticleMetadataEntity,

    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Comment? = null,

    @CreatedDate
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    val updatedAt: Instant = Instant.now()
)