package dev.codeswamp.core.article.application.base.snapshot

import dev.codeswamp.core.article.application.base.RebasePolicy
import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

/**
 * 각 article의 0번째 버전부터, 5번째 버전마다 스냅샷으로 저장
 */
@Component
@Primary
class SnapshotEvery5thPolicy(
    private val articleRepository: ArticleRepository
): RebasePolicy {
    override fun shouldStoreAsBase(version: Version): Boolean {
        return articleRepository.countVersionsOfArticle(version.articleId) % 5 == 0L
    }
}