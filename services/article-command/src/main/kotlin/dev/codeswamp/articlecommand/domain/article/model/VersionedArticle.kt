package dev.codeswamp.articlecommand.domain.article.model

import dev.codeswamp.articlecommand.domain.AggregateRoot
import dev.codeswamp.articlecommand.domain.DomainEvent
import dev.codeswamp.articlecommand.domain.article.event.DraftedEvent
import dev.codeswamp.articlecommand.domain.article.event.PublishedEvent
import dev.codeswamp.articlecommand.domain.article.event.VersionCreatedEvent
import dev.codeswamp.articlecommand.domain.article.exception.InvalidArticleStateException
import dev.codeswamp.articlecommand.domain.article.exception.PrivateArticleForbiddenException
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import java.time.Instant
import java.time.temporal.ChronoUnit

data class VersionedArticle private constructor(
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
    val isPublished: Boolean = false,//한번 publish된 이후에는 true를 유지

    /**
     * metadata: 가변 메타 데이터. 변경은 버전 관리에 포함되지 않는다.
     */
    val metadata: ArticleMetadata,

    val currentVersion: Version,

    ) : AggregateRoot() {
    companion object {
        fun create(
            id: Long,
            authorId: Long,
            createdAt: Instant,
            metadata: ArticleMetadata,
            title: String,
            diff: String,
            fullContent: String,
            versionId: Long
        ): VersionedArticle {

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

        fun of(
            id: Long, authorId: Long, createdAt: Instant, isPublished: Boolean,
            metadata: ArticleMetadata, currentVersion: Version
        ) = VersionedArticle(
            id = id,
            authorId = authorId,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
            isPublished = isPublished,
            metadata = metadata,
            currentVersion = currentVersion
        )
    }

    fun updateMetadata(metadata: ArticleMetadata): VersionedArticle {
        return this.copy(
            metadata = metadata,
        )
    }

    suspend fun updateVersionIfChanged(
        title: String,
        diff: String,
        generateId: suspend () -> Long,
        createdAt: Instant,
        shouldRebase: suspend (Version) -> Boolean,
        reconstructFullContent: suspend (Version) -> String,
    ): VersionedArticle {
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
                        if (shouldRebase(it)) {
                            it.asBaseVersion(reconstructFullContent(it))
                        } else it
                    }
            ).withEvent(
                VersionCreatedEvent(
                    articleId = id,
                    versionId = newVersionId
                )
            )
        }
    }

    suspend fun publish(slugUniquenessChecker: suspend (VersionedArticle, Long, Slug) -> Unit): VersionedArticle {
        val slug = metadata.slug ?: throw InvalidArticleStateException("Cannot publish article", "slug is null")

        slugUniquenessChecker(this, metadata.folderId, slug)

        return this.copy(
            isPublished = true,
            currentVersion = currentVersion.publish()
        ).withEvent(
            PublishedEvent(
                articleId = id,
                versionId = currentVersion.id,
                previousVersionId = currentVersion.previousVersionId,
            )
        )
    }

    suspend fun draft(slugUniquenessChecker: suspend (VersionedArticle, Long, Slug) -> Unit): VersionedArticle {
        if (isPublished) {
            val slug = metadata.slug
                ?: throw InvalidArticleStateException("Cannot draft article", "Published article should have slug")
            slugUniquenessChecker(this, metadata.folderId, slug)
        }

        return this.copy(currentVersion = currentVersion.draft()).withEvent(
            DraftedEvent(
                articleId = id,
                versionId = currentVersion.id,
            )
        )
    }

    fun archive(): VersionedArticle {
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

    fun withEvent(event: DomainEvent): VersionedArticle {
        val newArticle = this.copy()
        newArticle.addEvent(event)
        return newArticle
    }
}
