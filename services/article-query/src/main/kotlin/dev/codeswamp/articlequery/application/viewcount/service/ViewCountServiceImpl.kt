package dev.codeswamp.articlequery.application.viewcount.service

import dev.codeswamp.articlequery.application.context.Viewer
import dev.codeswamp.articlequery.application.viewcount.ViewCountRepository
import org.springframework.stereotype.Service

@Service
class ViewCountServiceImpl(
    private val viewCountRepository: ViewCountRepository
): ViewCountService {
    override suspend fun shouldBeCounted(viewer: Viewer, articleId: Long): Boolean {
        return viewCountRepository.existsByArticleIdAndViewer(articleId, viewer)
    }

    override suspend fun incrementIfNeeded(viewer: Viewer, articleId: Long){
        if (shouldBeCounted(viewer, articleId)) {
            viewCountRepository.increment(articleId, viewer)
        }
    }

    override suspend fun getTotalViews(articleId: Long): Int {
        return viewCountRepository.getTotalViews(articleId)
    }

    override suspend fun getArticlesTotalViews(articleIds: List<Long>): List<Pair<Long, Int>> {
        return viewCountRepository.getAllTotalViewsOfArticles(articleIds)
    }
}