package dev.codeswamp.articlequery.application.context

data class Viewer (
    val userId: Long?,
    val ipAddress: String?,
    val userAgent: String?
)

