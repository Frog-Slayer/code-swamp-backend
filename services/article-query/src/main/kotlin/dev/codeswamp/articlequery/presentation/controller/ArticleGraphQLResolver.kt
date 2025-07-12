package dev.codeswamp.articlequery.presentation.controller

import dev.codeswamp.articlequery.application.context.Viewer
import dev.codeswamp.databasequery.FieldSelection
import dev.codeswamp.articlequery.application.usecase.UseCaseOrchestrator
import dev.codeswamp.articlequery.presentation.dto.response.EnrichedArticleResponse
import dev.codeswamp.articlequery.presentation.dto.response.FolderResponse
import dev.codeswamp.authcommon.security.CustomUserDetails
import graphql.schema.DataFetchingEnvironment
import graphql.schema.DataFetchingFieldSelectionSet
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.ContextValue
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import java.time.Instant

@Controller
class ArticleGraphQLResolver(
    private val useCaseOrchestrator: UseCaseOrchestrator
) {
    private val logger = LoggerFactory.getLogger(ArticleGraphQLResolver::class.java)

    private fun FieldSelection.Companion.fromSelectionSet(selectionSet: DataFetchingFieldSelectionSet, rootName: String) : FieldSelection {
       val top = selectionSet.immediateFields.groupBy { it.name }

       val childSelections = top.mapValues { (_, fieldList) ->
           val nestedSelectionSet = fieldList.first().selectionSet
           if (nestedSelectionSet.immediateFields.isNotEmpty()) {
               fromSelectionSet(nestedSelectionSet, fieldList.first().name)
           } else {
               FieldSelection(fieldList.first().name, emptyMap())
           }
       }

       return FieldSelection(name = rootName, children = childSelections)
    }

    @QueryMapping
    suspend fun articleById(
        @AuthenticationPrincipal user: CustomUserDetails?,
        @Argument articleId: Long,
        @ContextValue("ipAddress") ipAddress: String?,
        @ContextValue("userAgent") userAgent: String?,
        env: DataFetchingEnvironment,
    ) : EnrichedArticleResponse {
        val viewer = Viewer(
            userId = user?.getId(),
            ipAddress = ipAddress,
            userAgent = userAgent
        )

        val fieldSelection = FieldSelection.fromSelectionSet(env.selectionSet, "article")

        val article = useCaseOrchestrator.getArticleByArticleId(viewer, articleId, fieldSelection)

        return EnrichedArticleResponse.from(article)
    }

    @QueryMapping
    suspend fun articleByPathAndSlug(
        @AuthenticationPrincipal user: CustomUserDetails?,
        @Argument path: String,
        @Argument slug: String,
        @ContextValue("ipAddress") ipAddress: String?,
        @ContextValue("userAgent") userAgent: String?,
        env: DataFetchingEnvironment,
    ): EnrichedArticleResponse {
        val userId = user?.getId()
        val fieldSelection = FieldSelection.fromSelectionSet(env.selectionSet, "article")

        val viewer = Viewer(
            userId = user?.getId(),
            ipAddress = ipAddress,
            userAgent = userAgent
        )

        val article = useCaseOrchestrator.getArticleByPathAndSlug( viewer, path, slug, fieldSelection)

        return EnrichedArticleResponse.from(article)
    }

    @QueryMapping
    suspend fun articlesByKeyword(keyword: String) : List<EnrichedArticleResponse> {
        TODO("not implemented")
    }

    @QueryMapping
    suspend fun foldersByOwnerId(
        @Argument ownerId: Long,
        env: DataFetchingEnvironment
    ): List<FolderResponse> {
        val fieldSelection = FieldSelection.fromSelectionSet(env.selectionSet, "folder")

        val folderList = useCaseOrchestrator.getAllFoldersForUser(ownerId, fieldSelection)
        return folderList.map { FolderResponse.from(it) }
    }

    @QueryMapping
    suspend fun recentArticles(
        @AuthenticationPrincipal user: CustomUserDetails?,
        @Argument limit: Int,
        @Argument lastArticleId: Long?,
        @Argument lastCreatedAt: Instant?,
        env: DataFetchingEnvironment
    ): List<EnrichedArticleResponse> {
        val userId = user?.getId()
        val fieldSelection = FieldSelection.fromSelectionSet(env.selectionSet, "articles")
        val articles = useCaseOrchestrator.getRecentArticles(userId, lastCreatedAt, lastArticleId, limit, fieldSelection)

        logger.info("articles: {}", articles)
        return articles.map { EnrichedArticleResponse.from(it) }
    }

    @QueryMapping
    suspend fun myFolders(
        @AuthenticationPrincipal user: CustomUserDetails,
        env: DataFetchingEnvironment
    ) : List<FolderResponse> {
        val userId = user.getId() ?: throw Exception("User not found")
        val fieldSelection = FieldSelection.fromSelectionSet(env.selectionSet, "folders")
        val folders = useCaseOrchestrator.getAllFoldersForUser(userId, fieldSelection)

        return folders.map { FolderResponse.from(it) }
    }
}
