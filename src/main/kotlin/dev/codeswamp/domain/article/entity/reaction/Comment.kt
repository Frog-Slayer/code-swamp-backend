package dev.codeswamp.domain.article.entity.reaction

import dev.codeswamp.domain.article.entity.article.ArticleMetadata
import dev.codeswamp.domain.user.entity.User
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
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_metadata_id")
    var articleMetadata: ArticleMetadata,

    var content: String,

    @CreatedDate
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    val updatedAt: Instant = Instant.now()


)