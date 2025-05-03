package dev.codeswamp.domain.article.infrastructure.persistence.entity.rdb

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
data class ArticleContentEntity (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "article_metadata_id")
    val articleMetadataEntity: ArticleMetadataEntity,

    var content: String,//raw markdown text

    @CreatedDate
    val createdAt: Instant = Instant.now(),
)