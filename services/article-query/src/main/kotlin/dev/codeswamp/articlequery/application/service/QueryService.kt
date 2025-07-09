package dev.codeswamp.articlequery.application.service

import dev.codeswamp.articlequery.application.dto.EnrichedArticle
import dev.codeswamp.articlequery.application.dto.UserContentBundle
import dev.codeswamp.articlequery.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlequery.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlequery.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.databasequery.FieldSelection
import dev.codeswamp.articlequery.application.service.mapper.ArticleFieldMapper
import dev.codeswamp.articlequery.application.service.mapper.FolderFieldMapper
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class QueryService(
    private val articleRepository: PublishedArticleRepository,
    private val folderRepository: FolderRepository,
    private val userProfileFetcher: UserProfileFetcher
) {

    suspend fun getArticleByArticleId(userId: Long?, articleId: Long, fields: FieldSelection) : EnrichedArticle {
        val articleFields = fields.selectedFields(ArticleFieldMapper)

        val article = articleRepository.findByArticleId(userId, articleId, articleFields)
            ?: throw ArticleNotFoundException.Companion.byId(articleId)

        val author = if (fields.includes("author")) {
            val authorId = requireNotNull(article.authorId)
            userProfileFetcher.fetchUserProfile(authorId)
        } else null

        val folder = if (fields.includes("folder")) {
            val folderId = requireNotNull(article.folderId)
            val folderFields = fields.forNested("folder").selectedFields(FolderFieldMapper)
            folderRepository.findById(folderId, folderFields)
                ?: throw FolderNotFoundException.Companion.byId(folderId)
        } else null


        return EnrichedArticle.from(article, author, folder)
    }

    suspend fun getArticleByPathAndSlug(userId: Long?, path: String, slug: String, fields: FieldSelection ) : EnrichedArticle {
        val folderFields = fields.forNested("folder").selectedFields(FolderFieldMapper) + "full_path"

        val folder = folderRepository.findFolderByFolderPath(path, folderFields)
            ?: throw FolderNotFoundException.byPath(path)

        val folderId = requireNotNull(folder.id)

        val articleField = fields.selectedFields(ArticleFieldMapper)

        val article = articleRepository.findByFolderIdAndSlug(userId, folderId, slug, articleField)
            ?: throw ArticleNotFoundException.Companion.bySlug("$path/$slug")

        val author = if (fields.includes("author")) {
            val authorId = requireNotNull(article.authorId)
            userProfileFetcher.fetchUserProfile(authorId)
        } else null

        return EnrichedArticle.from(article, author, folder)
    }

    suspend fun getArticleByArticleIdAndVersionId(userId: Long?, articleId: Long, versionId: Long, fields: FieldSelection) : EnrichedArticle {
        val articleFields = fields.selectedFields(ArticleFieldMapper) + "version_id"

        val article = articleRepository.findByArticleId(userId, articleId, articleFields)
            ?: throw ArticleNotFoundException.Companion.byId(articleId)

        if (article.versionId != versionId) {
            throw ArticleNotFoundException.byId(articleId)//VersionMismatchException
        }

        val author = if (fields.includes("author")) {
            val authorId = requireNotNull(article.authorId)
            userProfileFetcher.fetchUserProfile(authorId)
        } else null

        val folder = if (fields.includes("folder")) {
            val folderId = requireNotNull(article.folderId)
            val folderFields = fields.forNested("folder").selectedFields(FolderFieldMapper)
            folderRepository.findById(folderId, folderFields)
                ?: throw FolderNotFoundException.Companion.byId(folderId)
        } else null


        return EnrichedArticle.from(article, author, folder)
    }

    suspend fun getRecentArticles(
        userId: Long?,
        lastCreatedAt: Instant?,
        lastArticleId: Long?,
        limit: Int,
        fields: FieldSelection
    ) : List<EnrichedArticle> {
        val articleFields = fields.selectedFields(ArticleFieldMapper)
        val articles = articleRepository.findRecentArticles(userId, lastCreatedAt, lastArticleId, limit, articleFields)

        val authors= if (fields.includes("author")) {
            val userIds = articles.mapNotNull{ it.authorId }.distinct()
            userProfileFetcher.fetchUserProfiles(userIds).associateBy { it.userId }
        } else null

        val folders = if (fields.includes("folder")) {
            val folderIds = articles.mapNotNull{ it.folderId }.distinct()
            val folderFields = fields.forNested("folder").selectedFields(FolderFieldMapper) + "id"
            folderRepository.findAllByIds(folderIds, folderFields).associateBy { it.id }
        } else null


        return articles.map { article ->
            val author = authors?.get(article.authorId)
            val folder = folders?.get(article.folderId)
            EnrichedArticle.from(article, author, folder)
        }
    }

    suspend fun getAllFoldersForUser(userId: Long, fields: FieldSelection) : List<Folder> {
        val folderFields = fields.selectedFields(FolderFieldMapper)

        return folderRepository.findAllByOwnerId(userId, folderFields)
    }

    suspend fun getAllFoldersAndArticlesForUser(
        userId: Long?,
        ownerId: Long,
        fields: FieldSelection
    ) : UserContentBundle {
        val folderFields = fields.forNested("folder").selectedFields(FolderFieldMapper)
        val articleFields = fields.forNested("article").selectedFields(ArticleFieldMapper)

        val folders = folderRepository.findAllByOwnerId(ownerId, folderFields)
        val articles = articleRepository.findAllByAuthorId(userId, ownerId, articleFields)

        val authors = if (fields.includes("article.author")) {
            val authorIds = articles.mapNotNull { it.authorId }.distinct()
            userProfileFetcher.fetchUserProfiles(authorIds).associateBy { it.userId }
        } else null

        val enrichedArticles = articles.map { article ->
            val author = authors?.get(article.authorId)
            EnrichedArticle.from(article, author = author, folder = null)
        }

        return UserContentBundle(
            folders = folders,
            articles = enrichedArticles
        )
    }
}