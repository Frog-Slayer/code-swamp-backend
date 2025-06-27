package dev.codeswamp.articleprojection.application.event.event

import dev.codeswamp.core.application.event.ApplicationEvent
import java.time.Instant

/**
 * - 특정 Article의 Version이 'PUBLISHED' 상태로 전이될 떄 발행됨
 * - 발행 시점 해당 버전의 Snapshot
 * - 1) Read model을 갱신
 * - 2) Search Engine 인덱싱
 * - 3) TODO: 이후 알림 발송 서비스가 만들어지면 구독자에게 알림을 보냄
 */
data class ArticlePublishedEvent(
    val articleId: Long,
    val authorId: Long,
    val versionId: Long,

    val createdAt: Instant,
    val updatedAt: Instant,

    val folderId: Long,

    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic: Boolean,

    val slug: String,
    val title: String,
    val fullContent: String
) : ApplicationEvent



