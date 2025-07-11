package dev.codeswamp.articlequery.infrastructure.persistence.mapper

import dev.codeswamp.articlequery.application.mapper.ArticleFieldMapper
import dev.codeswamp.databasequery.FieldMapper
import org.springframework.stereotype.Component

@Component
class ArticleFieldMapperImpl : ArticleFieldMapper{
    private val graphqlToDbFieldMap = mapOf(
        "id" to "id",
        "author" to "author_id",
        "folder" to "folder_id",
        "versionId" to "version_id",
        "createdAt" to "created_at",
        "updatedAt" to "updated_at",
        "summary" to "summary",
        "thumbnail" to "thumbnail",
        "isPublic" to "is_public",
        "slug" to "slug",
        "title" to "title",
        "content" to "content",
    )

    override fun map(fields: Set<String>): Set<String> {
        return fields.mapNotNull { graphqlToDbFieldMap[it] }.toSet()
    }
}