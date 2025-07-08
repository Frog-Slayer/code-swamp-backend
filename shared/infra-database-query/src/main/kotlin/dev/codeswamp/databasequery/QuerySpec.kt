package dev.codeswamp.databasequery

data class QuerySpec(
    val selectFields: List<String>,
    val tableName: String,
    val whereConditions: List<String>,
    val params : Map<String, Any>,
    val orderBy: List<String>,
    val limit: Int?,
) {

    fun buildSql(): String {
        val select = selectFields.joinToString(",")
        val where = if (whereConditions.isEmpty()) ""
                        else "WHERE" + whereConditions.joinToString(" AND ") { "($it)" }
        val orderBy = if (this.orderBy.isEmpty()) "" else "ORDER BY ${this.orderBy.joinToString(", ")}"
        val limit = if (limit != null) "LIMIT :limit" else ""

        return listOf(
            "SELECT $select",
            "FROM $tableName",
            where,
            orderBy,
            limit,
        ).filter {  it.isNotBlank() }.joinToString("\n")
    }

    fun toQueryParams(): Map<String, Any> {
        return buildMap {
            putAll( params )
            limit?.let { put("limit", it) }
        }
    }
}
