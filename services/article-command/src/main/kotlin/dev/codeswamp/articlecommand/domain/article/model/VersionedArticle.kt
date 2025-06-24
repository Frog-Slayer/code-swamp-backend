package dev.codeswamp.articlecommand.domain.article.model

import dev.codeswamp.articlecommand.domain.article.event.ArticleDraftedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticlePublishedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticleVersionCreatedEvent
import dev.codeswamp.articlecommand.domain.article.exception.InvalidArticleStateException
import dev.codeswamp.articlecommand.domain.article.exception.PrivateArticleForbiddenException
import dev.codeswamp.articlecommand.domain.article.model.command.ArticleVersionUpdateCommand
import dev.codeswamp.articlecommand.domain.article.model.command.CreateArticleCommand
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.domain.AggregateRoot
import dev.codeswamp.core.domain.DomainEvent
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
     * hasBeenPublished: 한 번 publish된 이후에는 true를 유지.
     * Draft 상태에서는 false, publish 후에는 변경 금지.
     */
    val hasBeenPublished: Boolean = false,//한번 publish된 이후에는 true를 유지

    /**
     * metadata: 가변 메타 데이터. 변경은 버전 관리에 포함되지 않는다.
     */
    val metadata: ArticleMetadata,

    val currentVersion: Version,

    ) : AggregateRoot() {

    companion object {
        fun create(command: CreateArticleCommand): VersionedArticle {
            val articleId = command.generateId()

            val initialVersion = Version.create(
                id = command.generateId(),
                articleId = articleId,
                previousVersionId = null,
                title = command.title,
                diff = command.diff,
                createdAt = command.createdAt,
            ).withSnapshot(command.fullContent)

            val article = VersionedArticle(
                id = articleId,
                authorId = command.authorId,
                createdAt = command.createdAt.truncatedTo(ChronoUnit.MILLIS),
                metadata = command.metadata,
                currentVersion = initialVersion,
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
            hasBeenPublished = isPublished,
            metadata = metadata,
            currentVersion = currentVersion
        )
    }

    fun updateMetadata(metadata: ArticleMetadata): VersionedArticle {
        return if (this.metadata == metadata) this
        else this.copy( metadata = metadata )
    }

    private fun isVersionChangeNeeded(command : ArticleVersionUpdateCommand): Boolean {
        return command.hasMeaningfulDiff || command.title != currentVersion.title?.value
    }

    fun updateVersionIfChanged(command : ArticleVersionUpdateCommand) : VersionedArticle {
        if (!isVersionChangeNeeded(command)) return this
        val newVersionId = command.generateId()
        val newVersion = Version.create(
            id = newVersionId,
            title = command.title,
            diff = command.diff,
            createdAt = command.createdAt,
            articleId = id,
            previousVersionId = currentVersion.id
        )

        return this.copy(currentVersion = newVersion)
            .withEvent( ArticleVersionCreatedEvent( articleId = id,  versionId = newVersionId))
    }

    fun withSnapshot(snapshotContent: String?) : VersionedArticle {
        if (currentVersion.state != VersionState.NEW ) return this // 새 버전이 생성되지 않은 경우는 스냅샷 저장을 하지 않음
        return snapshotContent?. let {
            this.copy( currentVersion =  currentVersion.withSnapshot(snapshotContent))
        } ?:this
    }

    fun publish( fullContent: String ): VersionedArticle {
        if (metadata.slug == null) {
            throw InvalidArticleStateException("Cannot draft article", "Published article should have slug")
        }

        return this.copy(
            hasBeenPublished = true,
            currentVersion = currentVersion.publish()
        ).withEvent(
            ArticlePublishedEvent(
                articleId = id,
                versionId = currentVersion.id,
                previousVersionId = currentVersion.previousVersionId,
                fullContent = fullContent,
            )
        )
    }

    fun draft(): VersionedArticle {
        if (hasBeenPublished && metadata.slug == null) {
            throw InvalidArticleStateException("Cannot draft article", "Published article should have slug")
        }

        return this.copy(currentVersion = currentVersion.draft()).withEvent(
            ArticleDraftedEvent(
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

    fun withEvent(event: DomainEvent): VersionedArticle {
        return  this.copy().apply {
            addEvent(event)
        }
    }
}
