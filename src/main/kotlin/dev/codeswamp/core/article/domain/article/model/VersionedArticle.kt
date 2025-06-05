package dev.codeswamp.core.article.domain.article.model

import dev.codeswamp.core.article.domain.AggregateRoot
import dev.codeswamp.core.article.domain.article.events.ArticleDraftedEvent
import dev.codeswamp.core.article.domain.article.events.ArticlePublishedEvent
import dev.codeswamp.core.article.domain.article.events.ArticleVersionCreatedEvent
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import org.springframework.security.access.AccessDeniedException
import java.time.Instant

data class VersionedArticle (
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
    val isPublished : Boolean,//한번 publish된 이후에는 true를 유지

    /**
     * metadata: 가변 메타 데이터. 변경은 버전 관리에 포함되지 않는다.
     */
    val metadata: ArticleMetadata,

    val currentVersion: Version,
) : AggregateRoot() {

    fun updateMetadata(metadata: ArticleMetadata) : VersionedArticle {
        return this.copy(
            metadata = metadata,
        )
    }

    fun updateVersionIfChanged(
                diff: String,
                generateId: () -> Long,
                createdAt : Instant) : VersionedArticle {
        return if (diff.isBlank()) this
        else {
            addEvent(ArticleVersionCreatedEvent(
                articleId = id,
                versionId = currentVersion.id,
            ))

            this.copy(
                currentVersion = Version(
                    id = generateId(),
                    articleId = id,
                    state = ArticleState.NEW,
                    previousVersion = currentVersion,
                    diff = diff,
                    createdAt = createdAt,
                )
            )
        }
    }

    fun publish(slugUniquenessChecker : (Long, Long, String) -> Boolean) : VersionedArticle {
        // publish 시점 이후로 slug는 non-nullable, unique해야 함
        val slug = requireNotNull(metadata.slug?.value?.let { Slug.of(it)}) { "Slug is required" }
        require(slugUniquenessChecker(id, metadata.folderId, slug.value)) { "Metadata slug not set" }

        //이전 버전 및 이전 발행본을 ARCHIVED로 변경하는 이벤트 발행
        addEvent(ArticlePublishedEvent(
            articleId = id,
            versionId = currentVersion.id,
        ))

        return this.copy(
            isPublished = true,
            currentVersion = currentVersion.publish()
        )
    }

    fun draft(slugUniquenessChecker : (Long, Long, String) -> Boolean) : VersionedArticle {
        if (isPublished) {
            val slug = requireNotNull(metadata.slug?.value?.let { Slug.of(it)}) { "Slug is required" }
            require(slugUniquenessChecker(id, metadata.folderId, slug.value)) { "Metadata slug not set" }
        }

        addEvent(ArticleDraftedEvent(
            articleId = id,
            versionId = currentVersion.id,
        ))

        return this.copy(
            currentVersion = currentVersion.draft()
        )
    }

    fun archive() : VersionedArticle {
        return this.copy(
            currentVersion = currentVersion.publish()
        )
    }

    fun checkOwnership(userId: Long) {
        if (authorId != userId) throw AccessDeniedException("You are not the owner of this article")
    }

    fun assertReadableBy(userId: Long?) {
        if (!metadata.isPublic && (userId == null || authorId != userId))
            throw AccessDeniedException("cannot read private article")
    }
}
