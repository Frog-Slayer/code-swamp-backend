package dev.codeswamp.articlecommand.application.usecase.query.article.draft

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionState
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetDraftsUseCaseImpl(
    private val articleRepository: ArticleRepository,
) : GetDraftsUseCase {

    @Transactional(readOnly = true)
    override suspend fun handle(query: GetDraftsQuery): DraftList {
        val draftVersions = articleRepository.findByAuthorIdAndState(query.articleId, VersionState.DRAFT)

        return DraftList(draftVersions.map{ it -> it.toDraftItem() })
    }

    fun Version.toDraftItem(): DraftListItem = DraftListItem(
        articleId = articleId,
        versionId = id,
        createdAt = createdAt,
        title = title?.value ?: "",
    )

}