package dev.codeswamp.articlequery.application.usecase

import dev.codeswamp.articlequery.application.context.Viewer
import dev.codeswamp.articlequery.application.mapper.ArticleFieldMapper
import dev.codeswamp.articlequery.application.mapper.FolderFieldMapper
import dev.codeswamp.articlequery.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.viewcount.service.ViewCountService
import dev.codeswamp.articlequery.application.usecase.article.byauthor.GetArticlesByAuthorIdQuery
import dev.codeswamp.articlequery.application.usecase.article.byauthor.GetArticlesOfAuthorUseCase
import dev.codeswamp.articlequery.application.usecase.article.byid.GetArticleByIdQuery
import dev.codeswamp.articlequery.application.usecase.article.byid.GetArticleByIdUseCase
import dev.codeswamp.articlequery.application.usecase.article.byslug.GetArticleByPathAndSlugQuery
import dev.codeswamp.articlequery.application.usecase.article.byslug.GetArticleByFolderAndSlugUseCase
import dev.codeswamp.articlequery.application.usecase.article.recent.GetRecentArticlesQuery
import dev.codeswamp.articlequery.application.usecase.article.recent.GetRecentArticlesUseCase
import dev.codeswamp.articlequery.application.usecase.dto.EnrichedArticle
import dev.codeswamp.articlequery.application.usecase.dto.UserContentBundle
import dev.codeswamp.articlequery.application.usecase.folder.byid.GetFolderByIdQuery
import dev.codeswamp.articlequery.application.usecase.folder.byid.GetFolderByIdUseCase
import dev.codeswamp.articlequery.application.usecase.folder.byids.GetAllFoldersByIdsQuery
import dev.codeswamp.articlequery.application.usecase.folder.byids.GetAllFoldersByIdsUseCase
import dev.codeswamp.articlequery.application.usecase.folder.byowner.GetAllFoldersOfUserQuery
import dev.codeswamp.articlequery.application.usecase.folder.byowner.GetAllFoldersOfUserUseCase
import dev.codeswamp.articlequery.application.usecase.folder.bypath.GetFolderByPathQuery
import dev.codeswamp.articlequery.application.usecase.folder.bypath.GetFolderByPathUseCase
import dev.codeswamp.databasequery.FieldSelection
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UseCaseOrchestrator(
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val getArticleByFolderAndSlugUseCase: GetArticleByFolderAndSlugUseCase,
    private val getArticlesOfAuthorUseCase: GetArticlesOfAuthorUseCase,
    private val getRecentArticlesUseCase: GetRecentArticlesUseCase,
    private val userProfileFetcher: UserProfileFetcher,
    private val getFolderByIdUseCase: GetFolderByIdUseCase,
    private val getFolderByPathUseCase: GetFolderByPathUseCase,
    private val getAllFoldersByIdsUseCase: GetAllFoldersByIdsUseCase,
    private val getAllFoldersOfUserUseCase: GetAllFoldersOfUserUseCase,
    private val articleFieldMapper: ArticleFieldMapper,
    private val folderFieldMapper: FolderFieldMapper,
    private val viewService: ViewCountService
) {

    suspend fun getArticleByArticleId(viewer: Viewer, articleId: Long, fields: FieldSelection) : EnrichedArticle {
        val articleFields = fields.selectedFields(articleFieldMapper)

        val article = getArticleByIdUseCase.handle(GetArticleByIdQuery(
            userId = viewer.userId,
            articleId = articleId,
            fields = articleFields
        ))

        val author = if (fields.includes("author")) {
            val authorId = requireNotNull(article.authorId)
            userProfileFetcher.fetchUserProfile(authorId)
        } else null

        val folder = if (fields.includes("folder")) {
            val folderId = requireNotNull(article.folderId)
            val folderFields = fields.forNested("folder").selectedFields(folderFieldMapper)
            getFolderByIdUseCase.handle(GetFolderByIdQuery(
                    folderId, folderFields
            ))
        } else null

        viewService.incrementIfNeeded( viewer, articleId )

        val views = if ( fields.includes("views")) {
            viewService.getTotalViews(articleId)
        } else null

        return EnrichedArticle.from(article, author, folder, views)
    }

    suspend fun getArticleByPathAndSlug(viewer: Viewer, path: String, slug: String, fields: FieldSelection ) : EnrichedArticle {
        val articleField = fields.selectedFields(articleFieldMapper) + "id"
        val folderFields = fields.forNested("folder").selectedFields(folderFieldMapper) + "id"

        val folder = getFolderByPathUseCase.handle(GetFolderByPathQuery(
            path, folderFields
        ))

        val folderId = requireNotNull(folder.id)

        val article = getArticleByFolderAndSlugUseCase.handle(GetArticleByPathAndSlugQuery(
            viewer.userId, folderId, slug, articleField, folderFields
        ))


        val author = if (fields.includes("author")) {
            val authorId = requireNotNull(article.authorId)
            userProfileFetcher.fetchUserProfile(authorId)
        } else null

        val articleId = requireNotNull(article.id)

        viewService.incrementIfNeeded( viewer, articleId )

        val views = if ( fields.includes("views")) {
            viewService.getTotalViews(articleId)
        } else null

        return EnrichedArticle.from(article, author, folder, views)
    }

    suspend fun getRecentArticles(
        userId: Long?,
        lastCreatedAt: Instant?,
        lastArticleId: Long?,
        limit: Int,
        fields: FieldSelection
    ) : List<EnrichedArticle> {
        val articleFields = fields.selectedFields(articleFieldMapper) + "id"

        val articles = getRecentArticlesUseCase.handle(GetRecentArticlesQuery(
            userId, lastCreatedAt, lastArticleId, limit, articleFields
        ))

        val authors= if (fields.includes("author")) {
            val userIds = articles.mapNotNull{ it.authorId }.distinct()
            userProfileFetcher.fetchUserProfiles(userIds).associateBy { it.userId }
        } else null

        val folders = if (fields.includes("folder")) {
            val folderIds = articles.mapNotNull{ it.folderId }.distinct()
            val folderFields = fields.forNested("folder").selectedFields(folderFieldMapper) + "id"
            getAllFoldersByIdsUseCase.handle(GetAllFoldersByIdsQuery(
                 folderIds, folderFields
            )).associateBy { it.id }
        } else null

        val views = if (fields.includes("views")) {
            val articleIds = articles.mapNotNull { it.id }
            viewService.getArticlesTotalViews( articleIds ).associate { it.first to it.second }
        } else null

        return articles.map { article ->
            val author = authors?.get(article.authorId)
            val folder = folders?.get(article.folderId)
            val view = views?.get(article.id)
            EnrichedArticle.from(article, author, folder, view)
        }
    }

    suspend fun getAllFoldersForUser(userId: Long, fields: FieldSelection) : List<Folder> {
        val folderFields = fields.selectedFields(folderFieldMapper)

        return getAllFoldersOfUserUseCase.handle(GetAllFoldersOfUserQuery(
            userId, folderFields
        ))
    }

    suspend fun getAllFoldersAndArticlesForUser(
        userId: Long?,
        ownerId: Long,
        fields: FieldSelection
    ) : UserContentBundle {
        val folderFields = fields.forNested("folder").selectedFields(folderFieldMapper)
        val articleFields = fields.forNested("article").selectedFields(articleFieldMapper)

        val folders = getAllFoldersOfUserUseCase.handle(GetAllFoldersOfUserQuery(
            ownerId, folderFields
        ))

        val articles = getArticlesOfAuthorUseCase.handle(
            GetArticlesByAuthorIdQuery(
                userId, ownerId, articleFields
            )
        )

        val authors = if (fields.includes("article.author")) {
            val authorIds = articles.mapNotNull { it.authorId }.distinct()
            userProfileFetcher.fetchUserProfiles(authorIds).associateBy { it.userId }
        } else null

        val views = if (fields.includes("views")) {
            val articleIds = articles.mapNotNull { it.id }
            viewService.getArticlesTotalViews( articleIds ).associate { it.first to it.second }
        } else null

        val enrichedArticles = articles.map { article ->
            val author = authors?.get(article.authorId)
            val view = views?.get(article.id)
            EnrichedArticle.from(article, author = author, folder = null, view)
        }

        return UserContentBundle(
            folders = folders,
            articles = enrichedArticles
        )
    }
}