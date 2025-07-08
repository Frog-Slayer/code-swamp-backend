package dev.codeswamp.articlequery.application.service.mapper

interface FieldMapper {
    fun map(fields: Set<String>): Set<String>
}