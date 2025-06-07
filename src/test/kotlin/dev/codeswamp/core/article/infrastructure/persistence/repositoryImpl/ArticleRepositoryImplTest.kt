package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.core.article.domain.support.DiffProcessor
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
class ArticleRepositoryImplTest(
    @Autowired private val articleRepository: ArticleRepository,
    @Autowired private val slugChecker: SlugUniquenessChecker,
    @Autowired private val idGenerator: IdGenerator,
    @Autowired private val diffProcessor: DiffProcessor
){

    private val authorId = 100L
    private val createdAt = Instant.now()

    private val metadata = ArticleMetadata(
        folderId = 10L,
        slug = Slug.of("test-slug"),
        summary = "test",
        thumbnailUrl = "",
        isPublic = true,
    )

    private fun baseArticle() = VersionedArticle.create (
            id = idGenerator.generateId(),
            authorId = authorId,
            createdAt = createdAt,
            metadata = metadata,
            diff = "full-content",
            fullContent = "full-content",
            versionId = idGenerator.generateId(),
            title= "title"
    )

    @Test
    fun `새 Draft 저장 후 정상 조회`() {
        val article = baseArticle().draft(slugChecker::checkSlugUniqueness)

        articleRepository.save(article)

        val saved = articleRepository.findByIdAndVersionId(article.id, article.currentVersion.id)

        assertNotNull(saved)
        assertEquals(article, saved)
    }



}