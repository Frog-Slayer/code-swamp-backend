package dev.codeswamp.databasequery

import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

fun DatabaseClient.GenericExecuteSpec.bindAll(params: Map<String, Any>) : DatabaseClient.GenericExecuteSpec {
    var spec = this
    params.forEach { (key, value) ->
        spec = spec.bind(key, value)
    }
    return spec
}

suspend inline fun <reified T : Any, R> DatabaseClient.executeQuerySingle(
    querySpec: QuerySpec,
    crossinline mapToDomain: (T) -> R
): R? {
    val sql = querySpec.buildSql()
    val params = querySpec.toQueryParams()

    return this.sql(sql)
        .bindAll(params)
        .map { row, meta -> mapRowToDataClass<T>(row, meta) }
        .awaitOneOrNull()
        ?.let { mapToDomain(it) }
}

suspend inline fun <reified T : Any, R> DatabaseClient.executeQueryList(
    querySpec: QuerySpec,
    crossinline mapToDomain: (T) -> R
): List<R> {
    val sql = querySpec.buildSql()
    val params = querySpec.toQueryParams()

    return this.sql(sql)
        .bindAll(params)
        .map { row, meta -> mapRowToDataClass<T>(row, meta) }
        .all()
        .map { mapToDomain(it) }
        .collectList()
        .awaitSingle()
}

inline fun <reified T : Any> mapRowToDataClass(row: Row, meta: RowMetadata): T {
    val kClass = T::class
    val constructor = kClass.primaryConstructor!!
    val columnNames = meta.columnMetadatas.map { it.name }.toSet()

    val args = constructor.parameters.associateWith { param ->
        val name = param.name ?: error("Unnamed parameter not supported")
        val prop = kClass.memberProperties.find { it.name == name }
            ?: error("No property for constructor param: $name")

        val columnName = prop.findAnnotation<ColumnName>()?.value ?: name
        val type = (param.type.classifier as? KClass<*>)?.javaObjectType
            ?: error("Cannot determine Java type for $name")

        if (columnName !in columnNames) null
        else row.get(columnName, type)
    }

    return constructor.callBy(args)
}
