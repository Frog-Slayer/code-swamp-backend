package dev.codeswamp.core.article.domain.article.model

import dev.codeswamp.core.article.domain.AggregateRoot
import dev.codeswamp.core.article.domain.ArticleDomainEvent
import dev.codeswamp.core.article.domain.article.events.ArticleDraftedEvent
import dev.codeswamp.core.article.domain.article.events.ArticlePublishedEvent
import dev.codeswamp.core.article.domain.article.events.ArticleVersionCreatedEvent
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.model.vo.Title
import org.springframework.security.access.AccessDeniedException
import java.time.Instant

data class VersionedArticle private constructor (
    val id: Long,
    val authorId: Long,

    /**
     * createdAt: 문서 최초 생성 시점
     * updatedAt은 currentVersion의 createdAt과 동일하다.
     */
    val createdAt: Instant,

    /**
     * isPublished: 한 번 publish된 이후에는 true를 유지.
     * Draft 상태에서는 false, publish 후에는 변경 금지.
     */
    val isPublished : Boolean = false,//한번 publish된 이후에는 true를 유지

    /**
     * metadata: 가변 메타 데이터. 변경은 버전 관리에 포함되지 않는다.
     */
    val metadata: ArticleMetadata,

    val currentVersion: Version,

) : AggregateRoot() {
    companion object {
        fun create(id: Long,
                   authorId: Long,
                   createdAt: Instant,
                   metadata: ArticleMetadata,
                   title: String,
                   diff: String,
                   fullContent: String,
                   versionId: Long) : VersionedArticle {

            val article = VersionedArticle(
                    id = id,
                    authorId = authorId,
                    createdAt = createdAt,
                    metadata = metadata,
                    currentVersion = Version.of(
                        id = versionId,
                        articleId = id,
                        state = VersionState.NEW,
                        previousVersionId = null,
                        title = title,
                        diff = diff,
                        createdAt = createdAt,
                    )
                    .asBaseVersion(fullContent)
                )

            return article
        }

        fun of (id: Long, authorId: Long, createdAt: Instant, isPublished: Boolean,
                metadata: ArticleMetadata, currentVersion: Version) = VersionedArticle(
            id = id,
            authorId = authorId,
            createdAt = createdAt,
            isPublished = isPublished,
            metadata = metadata,
            currentVersion = currentVersion
        )
    }

    fun updateMetadata(metadata: ArticleMetadata) : VersionedArticle {
        return this.copy(
            metadata = metadata,
        )
    }

    fun updateVersionIfChanged(//TODO: title 변경도 추적할 것
                title: String,
                diff: String,
                generateId: () -> Long,
                createdAt : Instant) : VersionedArticle {
        return if (diff.isBlank()) this
        else {
            val newVersionId = generateId()

            this.copy(
                currentVersion = Version.of(
                    id = newVersionId,
                    articleId = id,
                    state = VersionState.NEW,
                    previousVersionId = currentVersion.id,
                    title = title,
                    diff = diff,
                    createdAt = createdAt,
                )
            ).withEvent(ArticleVersionCreatedEvent(
                articleId = id,
                versionId = newVersionId
            ))
        }
    }

    fun publish(slugUniquenessChecker : (VersionedArticle, Long, Slug) -> Unit) : VersionedArticle {
        // publish 시점 이후로 slug는 non-nullable, unique해야 함
        val slug = requireNotNull(metadata.slug) { "Slug is required" }
        slugUniquenessChecker(this, metadata.folderId, slug)

        //이전 버전 및 이전 발행본을 ARCHIVED로 변경하는 이벤트 발행

        return this.copy(
            isPublished = true,
            currentVersion = currentVersion.publish()
        ).withEvent(
            ArticlePublishedEvent(
            articleId = id,
            versionId = currentVersion.id,
            previousVersionId = currentVersion.previousVersionId,
        ))
    }

    fun draft(slugUniquenessChecker : (VersionedArticle, Long, Slug) -> Unit) : VersionedArticle {
        if (isPublished) {
            val slug = requireNotNull(metadata.slug) { "Slug is required" }
            slugUniquenessChecker(this, metadata.folderId, slug)
        }


        return this.copy( currentVersion = currentVersion.draft()).withEvent(
            ArticleDraftedEvent(
            articleId = id,
            versionId = currentVersion.id,
        ))
    }

    fun archive() : VersionedArticle {
        if (currentVersion.state == VersionState.PUBLISHED) throw IllegalStateException("Cannot archive current published version")

        return this.copy(
            currentVersion = currentVersion.archive()
        )
    }

    fun checkOwnership(userId: Long) {
        if (authorId != userId) throw AccessDeniedException("You are not the owner of this article")
    }

    fun assertReadableBy(userId: Long?) {
        if (!metadata.isPublic && (userId == null || authorId != userId))
            throw AccessDeniedException("cannot read private article")
    }

    fun withEvent(event: ArticleDomainEvent) : VersionedArticle {
        val newArticle = this.copy()
        newArticle.addEvent(event)
        return newArticle
    }
}
