package dev.codeswamp.databasequery

interface FieldMapper {
    fun map(fields: Set<String>): Set<String>
}