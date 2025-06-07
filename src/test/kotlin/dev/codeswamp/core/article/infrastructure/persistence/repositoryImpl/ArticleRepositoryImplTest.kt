package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.application.base.RebasePolicy
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.core.article.domain.support.DiffProcessor
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@Transactional
@Rollback(false)
class ArticleRepositoryImplTest(
    @Autowired private val articleRepository: ArticleRepository,
    @Autowired private val slugChecker: SlugUniquenessChecker,
    @Autowired private val idGenerator: IdGenerator,
    @Autowired private val diffProcessor: DiffProcessor,
    @Autowired private val rebasePolicy: RebasePolicy
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

    @Test
    fun `diff 업데이트 후 저장`() {
        val article = baseArticle().draft(slugChecker::checkSlugUniqueness)

        articleRepository.save(article)

        val updated = article.updateVersionIfChanged(
            diff = "+++updated",
            title = "title2",
            generateId = idGenerator::generateId,
            createdAt = Instant.now(),
            shouldRebase = { version -> false },
            reconstructFullContent = { version -> "" }
        ).draft(slugChecker::checkSlugUniqueness)

        articleRepository.save(updated)

        val prev = articleRepository.findByIdAndVersionId(article.id, article.currentVersion.id)
        val saved = articleRepository.findByIdAndVersionId(article.id, updated.currentVersion.id)

        assertThat(prev?.currentVersion?.title?.value).isEqualTo("title")
        assertThat(saved?.currentVersion?.title?.value).isEqualTo("title2")
        assertThat(saved).isEqualTo(updated)
    }
}