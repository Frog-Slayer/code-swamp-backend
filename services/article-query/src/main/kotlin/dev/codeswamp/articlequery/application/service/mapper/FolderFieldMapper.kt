package dev.codeswamp.articlequery.application.service.mapper

object FolderFieldMapper : FieldMapper {
    private val graphqlToDbFieldMap = mapOf(
        "id" to "id",
        "ownerId" to "owner_id",
        "name" to "name",
        "fullPath" to "full_path",
        "parentId" to "parent_id"
    )

    override fun map(fields: Set<String>): Set<String> {
        return fields.mapNotNull { graphqlToDbFieldMap[it] }.toSet()
    }
}