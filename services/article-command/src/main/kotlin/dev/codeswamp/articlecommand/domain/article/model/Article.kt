package dev.codeswamp.articlecommand.domain.article.model

import dev.codeswamp.articlecommand.domain.article.event.ArticleDeletedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticleDraftedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticlePublishedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticleVersionCreatedEvent
import dev.codeswamp.articlecommand.domain.article.exception.InvalidArticleStateException
import dev.codeswamp.articlecommand.domain.article.exception.PrivateArticleForbiddenException
import dev.codeswamp.articlecommand.domain.article.model.command.ArticleVersionUpdateCommand
import dev.codeswamp.articlecommand.domain.article.model.command.CreateArticleCommand
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Title
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import dev.codeswamp.core.domain.AggregateRoot
import dev.codeswamp.core.domain.DomainEvent
import java.time.Instant
import java.time.temporal.ChronoUnit

data class Article private constructor(
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

    val versionTree: VersionTree
) : AggregateRoot() {

    companion object {
        fun create(command: CreateArticleCommand): Pair<Article, Version> {
            val articleId = command.generateId()

            val initialVersion = Version.create(
                id = command.generateId(),
                ownerId = command.authorId,
                articleId = articleId,
                parentId = null,
                title = command.title,
                diff = command.diff,
                createdAt = command.createdAt,
            )

            val article = Article(
                id = articleId,
                authorId = command.authorId,
                createdAt = command.createdAt.truncatedTo(ChronoUnit.MILLIS),
                metadata = command.metadata,
                versionTree = VersionTree.createWithInitialVersion(initialVersion)
            )

            return article to initialVersion
        }

        fun of(
            id: Long, authorId: Long, createdAt: Instant, isPublished: Boolean,
            metadata: ArticleMetadata, versionList: List<Version>
        ) = Article(
            id = id,
            authorId = authorId,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
            isPublished = isPublished,
            metadata = metadata,
            versionTree = VersionTree.of(versionList.associateBy { it.id })
        )
    }

    fun updateMetadata(metadata: ArticleMetadata): Article {
        return if (this.metadata == metadata) this
        else this.copy( metadata = metadata )
    }

    private fun isVersionChangeNeeded(command : ArticleVersionUpdateCommand): Boolean {
        fun String.hasValidDelta() : Boolean = command.diffProcessor.hasValidDelta(this)

        val prevTitle = versionTree.versions[command.currentVersionId]?.title
        val hasMeaningfulDiff = command.diff.hasValidDelta()

        return hasMeaningfulDiff || prevTitle != Title.of(command.title)
    }

    fun updateVersionIfChanged(command : ArticleVersionUpdateCommand) : Pair<Article, Version> {
        if (!isVersionChangeNeeded(command)) {
            val currentVersion = versionTree.get(command.currentVersionId)
            return Pair(this, currentVersion)
        }

        val newVersionId = command.generateId()
        val newVersion = Version.create(
            id = newVersionId,
            ownerId = authorId,
            title = command.title,
            diff = command.diff,
            createdAt = command.createdAt,
            articleId = id,
            parentId = command.currentVersionId
        )

        val articleWithUpdatedVersionTree = this.copy(
            versionTree = versionTree.append(newVersion)
        ).withEvent(
            ArticleVersionCreatedEvent(articleId = id, versionId = newVersionId)
        )

        return articleWithUpdatedVersionTree to newVersion
    }

    fun getVersion(versionId: Long) : Version = versionTree.get(versionId)

    fun restoreFullContent(versionId: Long, diffProcessor: DiffProcessor) : String {
        fun String.applyDiff(diff: String) : String = diffProcessor.applyDiff(this, diff)

        return versionTree
            .findDiffChainTo( versionId )
            .fold("") { content, diff -> content.applyDiff(diff) }
    }

    fun publish( versionId: Long, fullContent: String ): Article {
        if (metadata.slug == null) {
            throw InvalidArticleStateException("Cannot publish article", "Slug is needed for publishing")
        }

        val toPublish = versionTree.get(versionId)

        if (toPublish.title == null) {
            throw InvalidArticleStateException("Cannot publish article", "Title is needed for publishing")
        }

        return this.copy(
            isPublished = true,
            versionTree = versionTree.publish(versionId)
        ).withEvent(
            ArticlePublishedEvent(
                articleId = id,
                versionId = versionId,
                authorId = authorId,
                createdAt = createdAt,
                updatedAt = toPublish.createdAt,
                folderId = metadata.folderId,
                summary = metadata.summary,
                thumbnailUrl = metadata.thumbnailUrl,
                isPublic = metadata.isPublic,
                slug = metadata.slug.value,
                title = toPublish.title.value,
                fullContent = fullContent,
            )
        )
    }

    fun draft( versionId : Long ): Article {
        if (isPublished && metadata.slug == null) {
            throw InvalidArticleStateException("Cannot draft article", "Published article should have slug")
        }

        return this.copy(versionTree = versionTree.draft( versionId )).withEvent(
            ArticleDraftedEvent(
                articleId = id,
                versionId = versionId
            )
        )
    }

    fun archive( versionId : Long ): Article {
        return this.copy(
            versionTree = versionTree.archive(versionId)
        )
    }

    fun diff(other: Article) : Pair<Boolean, VersionTreeDiff> {
        val metadataChanged = this.metadata != other.metadata
        val versionDiff = this.versionTree.diff(other.versionTree)
        return metadataChanged to versionDiff
    }

    fun delete() : Article {
        return this.withEvent(ArticleDeletedEvent(id))
    }

    fun checkOwnership(userId: Long) {
        if (authorId != userId) throw PrivateArticleForbiddenException(id)
    }

    fun withEvent(event: DomainEvent): Article {
        return this.also {
            addEvent(event)
        }
    }
}
