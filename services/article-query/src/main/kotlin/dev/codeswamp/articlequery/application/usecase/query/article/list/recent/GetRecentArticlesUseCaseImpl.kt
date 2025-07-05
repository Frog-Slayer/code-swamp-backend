package dev.codeswamp.articlequery.application.usecase.query.article.list.recent

import dev.codeswamp.articlequery.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.articlequery.application.usecase.query.article.list.ArticleListItem
import org.springframework.stereotype.Service

@Service
class GetRecentArticlesUseCaseImpl(
    private val publishedArticleRepository: PublishedArticleRepository,
    private val folderRepository: FolderRepository,
    private val userProfileFetcher: UserProfileFetcher
) : GetRecentArticlesUseCase {
    override suspend fun getRecentArticles(query: GetRecentArticlesQuery): List<ArticleListItem> {
        val articles = publishedArticleRepository.findRecentArticles(
            query.userId, query.lastCreatedAt, query.lastArticleId, query.limit)

        val folderIds = articles.map { it.folderId }.distinct()
        val folders = folderRepository.findAllByIds(folderIds).associateBy { it.id }

        val userIds = articles.map { it.authorId }.distinct()
        val userProfiles = userProfileFetcher.fetchUserProfiles(userIds).associateBy { it.userId }

        return articles.mapNotNull { it ->
            val fullPath = folders[it.folderId]?.fullPath ?: return@mapNotNull null
            val profile = userProfiles[it.authorId]?: return@mapNotNull null

            ArticleListItem(
                id = it.id,
                authorId = it.authorId,
                authorName = profile.username,
                authorNickname = profile.nickname,
                authorProfileImage =  profile.profileImage,
                folderPath = fullPath,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                summary = it.summary,
                thumbnailUrl = it.thumbnail,
                isPublic = it.isPublic,
                slug = it.slug,
                title = it.title,
            )
        }
    }
}
