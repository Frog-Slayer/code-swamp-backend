package dev.codeswamp.domain.article.entity.rdb.article

import dev.codeswamp.domain.article.entity.rdb.folder.Folder
import dev.codeswamp.domain.article.entity.rdb.reaction.Comment
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
data class ArticleMetadata(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    val writer: User,

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    var folder: Folder,

    @CreatedDate
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    var updatedAt: Instant = Instant.now(),

    var isPublic: Boolean,

    val currentVersion: Long,//현재 컨텐츠 버전

    @OneToMany(mappedBy = "articleMetadata", fetch = FetchType.LAZY)
    val contentVersions: List<ArticleContent> = mutableListOf(),

    @OneToMany(mappedBy = "articleMetadata", fetch = FetchType.LAZY)
    val comments: List<Comment> = mutableListOf(),

    @OneToMany
    val views: List<ArticleView> = mutableListOf(),
)
