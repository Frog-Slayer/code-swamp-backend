package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.article.domain.support.DiffProcessor
import dev.codeswamp.article.infrastructure.persistence.graph.repository.VersionNodeRepository
import dev.codeswamp.core.article.application.rebase.RebasePolicy
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
    @Autowired private val contentReconstructor: ArticleContentReconstructor,
    @Autowired private val versionNodeRepository: VersionNodeRepository,
    @Autowired private val diffProcessor: DiffProcessor,
    @Autowired private val rebasePolicy: RebasePolicy
) {

    private val authorId = 100L
    private val createdAt = Instant.now()

    private val metadata = ArticleMetadata(
        folderId = 10L,
        slug = Slug.of("test-slug"),
        summary = "test",
        thumbnailUrl = "",
        isPublic = true,
    )

    private fun baseArticle() = VersionedArticle.create(
        id = idGenerator.generateId(),
        authorId = authorId,
        createdAt = createdAt,
        metadata = metadata,
        diff = "",
        fullContent = "",
        versionId = idGenerator.generateId(),
        title = "title"
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

    @Test
    fun `Rebase 테스트`() {
        val article = baseArticle().draft(slugChecker::checkSlugUniqueness)
        articleRepository.save(article)

        var updated: VersionedArticle = article
        val fullContent = """
            훌쩍거리는 날도 아닌데
            훌쩍 네 향기가 날 듯 말 듯 해
            꾀병 같지만 오래전부터 난
            이미 앓아왔던 lovesick
            어느 날은 네 이름만 보일 때면
            손바닥으로 가리고
            우물쭈물 네 정거장을 지나고
            애타는 발걸음만 느려지네
            라!디오에서! 물결 같은 너의 모든 말들이
            재잘거리며 새벽의 입술로 나를 어질어질하게
        """.trimIndent().lines()

        for (i in 1..10) {
            val prev = fullContent.take(i - 1).joinToString("\n")
            val cur = fullContent.take(i).joinToString("\n")
            val diff = diffProcessor.calculateDiff(prev, cur)

            updated = articleRepository.save(
                updated.updateVersionIfChanged(
                    title = "title$i",
                    diff = diff ?: "",
                    generateId = idGenerator::generateId,
                    createdAt = Instant.now(),
                    shouldRebase = rebasePolicy::shouldStoreAsBase,
                    reconstructFullContent = contentReconstructor::reconstructFullContent
                )
                    .draft(slugUniquenessChecker = slugChecker::checkSlugUniqueness)
            )
        }

        val content = contentReconstructor.reconstructFullContent(updated.currentVersion)
        println(content)
        assertEquals(content, fullContent.joinToString("\n"))
    }

    @Test
    fun `neo4j query test`() {
        val base = versionNodeRepository.findBaseNodeNearestTo(7329755400482324480L)

        assertEquals(
            base?.versionId,
            7329755393112932353
        )
    }
}