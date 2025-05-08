package dev.codeswamp.core.article.infrastructure.persistence.entity

import dev.codeswamp.core.comment.entity.Comment
import dev.codeswamp.core.folder.domain.entity.Folder
import dev.codeswamp.core.folder.infrastructure.persistence.entity.FolderEntity
import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
data class ArticleMetadataEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    val authorId: Long,

    @Column(nullable = false)
    var folderId: Long,

    @CreatedDate
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    var updatedAt: Instant = Instant.now(),

    var isPublic: Boolean,

    var currentVersion: Long? = null,//현재 컨텐츠 버전

    val comments: MutableList<Long> = mutableListOf(),

    @OneToMany(mappedBy = "articleMetadataEntity", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    val contentVersions: MutableList<ArticleContentEntity> = mutableListOf(),

    @OneToMany
    val views: MutableList<ArticleView> = mutableListOf(),
)
