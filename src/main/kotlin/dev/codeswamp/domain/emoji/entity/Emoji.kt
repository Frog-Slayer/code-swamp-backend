package dev.codeswamp.domain.emoji.entity

import dev.codeswamp.domain.article.entity.rdb.ArticleMetadata
import dev.codeswamp.domain.user.entity.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class Emoji (

    @Id
    @GeneratedValue
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "article_metadata_id")
    val articleMetadata: ArticleMetadata,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    val emoji: String,
)