package dev.codeswamp.articleprojection.application.event.event

import dev.codeswamp.core.application.event.ApplicationEvent

/**
 * Domain Event: ArticleDeletedEvent
 * - 특정 Article을 삭제하는 경우 생성
 * - article-projection 서비스로 보내 발행본이 있다면 삭제해야 함
 * - neo4j에 저장된 해당 글의 버전 트리를 삭제
 */

data class ArticleDeletedEvent(
    val articleId: Long,
) : ApplicationEvent



