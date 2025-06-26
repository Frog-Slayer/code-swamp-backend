package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("snapshot")
data class ArticleSnapshotEntity(
    @Id
    @Column("version_id")
    val versionId: Long,
    val content: String,
)