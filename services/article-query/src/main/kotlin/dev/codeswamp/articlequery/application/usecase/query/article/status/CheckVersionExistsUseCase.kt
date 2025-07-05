package dev.codeswamp.articlequery.application.usecase.query.article.status

interface CheckVersionExistsUseCase {
    suspend fun exists(query: CheckVersionExistsQuery): CheckVersionExistsResult
}