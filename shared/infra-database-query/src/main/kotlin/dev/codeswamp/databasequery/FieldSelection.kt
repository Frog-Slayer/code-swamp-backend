package dev.codeswamp.databasequery

data class FieldSelection(
    val name: String,
    val children: Map<String, FieldSelection>
) {
    companion object

    fun includes(fieldName: String): Boolean = children.containsKey(fieldName)

    fun forNested(fieldName: String): FieldSelection =
        children[fieldName] ?: FieldSelection(fieldName, emptyMap())

    fun selectedFields(mapper: FieldMapper): Set<String> = mapper.map(children.keys)
}