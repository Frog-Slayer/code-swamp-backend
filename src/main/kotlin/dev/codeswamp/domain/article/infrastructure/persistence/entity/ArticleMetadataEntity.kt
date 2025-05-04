package dev.codeswamp.domain.article.infrastructure.persistence.entity

import dev.codeswamp.domain.comment.entity.Comment
import dev.codeswamp.domain.folder.domain.entity.Folder
import dev.codeswamp.domain.user.entity.User
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

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    val writer: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "folder_id")
    var folder: Folder,

    @CreatedDate
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    var updatedAt: Instant = Instant.now(),

    var isPublic: Boolean,

    val currentVersion: Long,//현재 컨텐츠 버전

    @OneToMany(mappedBy = "articleMetadataEntity", fetch = FetchType.LAZY)
    val contentVersions: List<ArticleContentEntity> = mutableListOf(),

    @OneToMany(mappedBy = "articleMetadataEntity", fetch = FetchType.LAZY)
    val comments: List<Comment> = mutableListOf(),

    @OneToMany
    val views: List<ArticleView> = mutableListOf(),
)
