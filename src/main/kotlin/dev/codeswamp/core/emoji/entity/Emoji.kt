package dev.codeswamp.core.emoji.entity

import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
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
    @JoinColumn(name = "article_id")
    val articleId: Long,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    val emoji: String,
)