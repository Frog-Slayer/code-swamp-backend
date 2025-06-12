package dev.codeswamp.core.article.domain.article.event

import dev.codeswamp.core.article.domain.ArticleDomainEvent

/**
 * Domain Event: ArticlePublishedEvent
 * - 특정 Article의 Version이 새로 만들어졌을 때 발행
 * - 1) 이 SnapshotStrategy에 따라 스냅샷을 따로 저장할 것인지를 확인 후 저장
 */
data class ArticleVersionCreatedEvent (
    val articleId: Long,
    val versionId: Long,
) : ArticleDomainEvent