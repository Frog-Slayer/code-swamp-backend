package dev.codeswamp.articlequery.application.usecase.article.byslug

import dev.codeswamp.articlequery.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import org.springframework.stereotype.Service

@Service
class GetArticleByFolderAndSlugUseCaseImpl(
    private val articleRepository: PublishedArticleRepository,
    private val folderRepository: FolderRepository,
) : GetArticleByFolderAndSlugUseCase {
    override suspend fun handle(query: GetArticleByPathAndSlugQuery) : PublishedArticle {
        return articleRepository.findByFolderIdAndSlug(query.userId, query.folderId, query.slug, query.articleFields)
            ?: throw ArticleNotFoundException.Companion.bySlug(query.slug)
    }
}