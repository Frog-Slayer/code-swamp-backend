package dev.codeswamp.article.infrastructure.persistence.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class BaseVersionEntity (
    @Id
    val versionId: Long,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,
)