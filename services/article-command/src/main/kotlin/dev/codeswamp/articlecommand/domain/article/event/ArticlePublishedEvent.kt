package dev.codeswamp.articlecommand.domain.article.event

import dev.codeswamp.core.domain.DomainEvent

/**
 * Domain Event: ArticlePublishedEvent
 * - 특정 Article의 Version이 'PUBLISHED' 상태로 전이될 떄 발행됨
 * - 1) 이전 버전 및 이전 발행본을 ARCHIVED 상태로 전이
 * - 2) Read model을 갱신
 * - 3) TODO: 이후 알림 발송 서비스가 만들어지면 구독자에게 알림을 보냄
 */
data class ArticlePublishedEvent(
    val articleId: Long,
    val previousVersionId: Long?,
    val versionId: Long,
) : DomainEvent



