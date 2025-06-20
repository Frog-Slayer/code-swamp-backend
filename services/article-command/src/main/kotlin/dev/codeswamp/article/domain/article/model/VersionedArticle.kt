package dev.codeswamp.article.domain.article.model

import dev.codeswamp.article.domain.AggregateRoot
import dev.codeswamp.article.domain.ArticleDomainEvent
import dev.codeswamp.article.domain.article.event.ArticleDraftedEvent
import dev.codeswamp.article.domain.article.event.ArticlePublishedEvent
import dev.codeswamp.article.domain.article.event.ArticleVersionCreatedEvent
import dev.codeswamp.article.domain.article.exception.InvalidArticleStateException
import dev.codeswamp.article.domain.article.exception.PrivateArticleForbiddenException
import dev.codeswamp.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.article.domain.article.model.vo.Slug
import java.time.Instant
import java.time.temporal.ChronoUnit

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
                    createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
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
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
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

    fun updateVersionIfChanged(
            title: String,
            diff: String,
            generateId: () -> Long,
            createdAt : Instant,
            shouldRebase: (Version) -> Boolean,
            reconstructFullContent: (Version) -> String,
        ) : VersionedArticle {
        return if (diff.isBlank() && title == this.currentVersion.title?.value) this
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
                .let {
                    if (shouldRebase(it)) { it.asBaseVersion(reconstructFullContent(it)) }
                    else it
                }
            ).withEvent(
                ArticleVersionCreatedEvent(
                    articleId = id,
                    versionId = newVersionId
                )
            )
        }
    }

    fun publish(slugUniquenessChecker : (VersionedArticle, Long, Slug) -> Unit) : VersionedArticle {
        val slug = metadata.slug ?:throw InvalidArticleStateException("Cannot publish article", "slug is null")

        slugUniquenessChecker(this, metadata.folderId, slug)

        return this.copy(
            isPublished = true,
            currentVersion = currentVersion.publish()
        ).withEvent(
            ArticlePublishedEvent(
                articleId = id,
                versionId = currentVersion.id,
                previousVersionId = currentVersion.previousVersionId,
            )
        )
    }

    fun draft(slugUniquenessChecker : (VersionedArticle, Long, Slug) -> Unit) : VersionedArticle {
        if (isPublished) {
            val slug = metadata.slug
                ?:throw InvalidArticleStateException("Cannot draft article", "Published article should have slug")
            slugUniquenessChecker(this, metadata.folderId, slug)
        }

        return this.copy( currentVersion = currentVersion.draft()).withEvent(
            ArticleDraftedEvent(
                articleId = id,
                versionId = currentVersion.id,
            )
        )
    }

    fun archive() : VersionedArticle {
        if (currentVersion.state == VersionState.PUBLISHED)
            throw InvalidArticleStateException("Invalid state transition", "cannot archive published version")

        return this.copy(
            currentVersion = currentVersion.archive()
        )
    }

    fun checkOwnership(userId: Long) {
        if (authorId != userId) throw PrivateArticleForbiddenException(id)
    }

    fun assertReadableBy(userId: Long?) {
        if (!metadata.isPublic && (userId == null || authorId != userId))
            throw PrivateArticleForbiddenException(id)
    }

    fun withEvent(event: ArticleDomainEvent) : VersionedArticle {
        val newArticle = this.copy()
        newArticle.addEvent(event)
        return newArticle
    }
}
