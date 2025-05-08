package dev.codeswamp.core.article.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "article_diff")
data class ArticleDiffEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    val article: ArticleEntity,

    @Column(nullable = false)
    val version: Long,

    @Column(nullable = false, columnDefinition = "TEXT")
    val diffData: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)
