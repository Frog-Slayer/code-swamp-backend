package dev.codeswamp.articlecommand.application.rebase

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

/**
 * 각 article의 0번째 버전부터, 5번째 버전마다 스냅샷으로 저장
 */
@Component
@Primary
class SnapshotEvery5thPolicy(
    private val articleRepository: ArticleRepository
) : SnapshotPolicy {

    override suspend fun shouldSaveAsSnapshot( article: VersionedArticle ) : Boolean {
        val isEvery5thSave = articleRepository.countVersionsOfArticle(article.id) % 5 == 0L

        return isEvery5thSave
    }
}