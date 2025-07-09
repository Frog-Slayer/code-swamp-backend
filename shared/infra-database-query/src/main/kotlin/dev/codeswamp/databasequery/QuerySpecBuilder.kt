package dev.codeswamp.databasequery

class QuerySpecBuilder(private val tableName: String) {
    private val selectFields = mutableListOf<String>()
    private val whereConditions = mutableListOf<String>()
    private val params: MutableMap<String, Any> = mutableMapOf()
    private val orderBy: MutableList<String> = mutableListOf()
    private var limit: Int? = null

    fun select(vararg fields: String) = apply {
        selectFields.addAll(fields)
    }

    fun where(vararg conditions: String) = apply {
        whereConditions.addAll(conditions)
    }

    fun bind(key: String, value: Any) = apply {
        params[key] = value
    }

    fun orderBy(vararg order: String) = apply {
        orderBy.addAll(order)
    }

    fun limit(value: Int) = apply {
        limit = value
    }

    fun build(): QuerySpec = QuerySpec(
        selectFields = selectFields,
        tableName = tableName,
        whereConditions = whereConditions.toList(),
        params = params.toMap(),
        orderBy = orderBy.toList(),
        limit = limit
    )
}
