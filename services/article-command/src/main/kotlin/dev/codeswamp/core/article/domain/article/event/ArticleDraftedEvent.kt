package dev.codeswamp.core.article.domain.article.event

import dev.codeswamp.core.article.domain.ArticleDomainEvent

/**
 * Domain Event: ArticleDraftedEvent
 * - 특정 Article의 Version이 'DRAFT' 상태로 전이될 떄 발행됨
 * - 이전 버전이 PUBLISHED가 아니라면 ARCHIVED 상태로 전이
 */

data class ArticleDraftedEvent (
    val articleId: Long,
    val versionId: Long,
): ArticleDomainEvent



