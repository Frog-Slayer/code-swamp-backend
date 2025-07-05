package dev.codeswamp.articlequery.application.usecase.query.article.status

import dev.codeswamp.articlequery.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlequery.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import org.springframework.stereotype.Service

@Service
class CheckVersionExistsUseCaseImpl(
    private val publishedArticleRepository: PublishedArticleRepository,
    private val folderRepository: FolderRepository
): CheckVersionExistsUseCase {
    override suspend fun exists(query: CheckVersionExistsQuery): CheckVersionExistsResult {
        val articleSummary = publishedArticleRepository.findArticleSummaryByArticleId(query.articleId)
            ?: throw ArticleNotFoundException.byId(query.articleId)

        if (articleSummary.versionId != query.versionId) throw ArticleNotFoundException.byId(query.articleId)

        val folder = folderRepository.findById(articleSummary.folderId)
            ?: throw FolderNotFoundException.byId(articleSummary.folderId)

        val fullPath = "${folder.fullPath}/${articleSummary.slug}"

        return CheckVersionExistsResult(
            fullPath = fullPath,
        )
    }
}