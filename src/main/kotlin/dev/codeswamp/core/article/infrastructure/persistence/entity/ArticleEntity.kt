package dev.codeswamp.core.article.infrastructure.persistence.entity

import jakarta.persistence.Entity

@Entity
class ArticleEntity {

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
}