package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleType
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import dev.codeswamp.core.article.domain.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.graph.repository.HistoryNodeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArticleServiceTest (
    @Autowired private val historyNodeRepository: HistoryNodeRepository,
    @Autowired private val historyService: ArticleHistoryService,
    @Autowired private val articleService: ArticleService,
){

    @BeforeAll
    fun create() {
        val article = Article(
            title = "title",
            type = ArticleType.NEW,
            authorId = 1L,
            folderId = 1L,
            isPublic = true,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            content = "this is my content"
        )

        val saved = articleService.create(article)

        assertThat(saved).isNotNull()
        assertThat(saved.title).isEqualTo("title")
        assertThat(saved.id).isEqualTo(1L)
    }

    @Test
    fun contentUpdateTest() {
        val article = articleService.findById(1L) ?: throw Exception("something went wrong")

        val updateString = "this content is updated"
        article.content = updateString

        val saved = articleService.update(article)

        assertThat(saved).isNotNull()
        assertThat(saved.content).isEqualTo(updateString)
        assertThat(saved.id).isEqualTo(1L)

        val currentVersion = saved.currentVersion?:throw Exception("something went wrong")
        val node = historyNodeRepository.findByDiffId(currentVersion)

        assertThat(node?.previous).isNotNull()
    }

    @Test
    fun metadataChangeTest() {
        val article = articleService.findById(1L)?.copy(
            title = "title22",
            type = ArticleType.PUBLISHED,
            authorId = 1L,
            folderId = 1L,
            isPublic = false,
        ) ?: throw Exception("something went wrong")

        val saved = articleService.update(article)

        assertThat(saved).isNotNull()
        assertThat(saved.id).isEqualTo(1L)
        assertThat(saved.title).isEqualTo("title22")
        assertThat(saved.currentVersion).isEqualTo(1L)
    }

    @Test
    fun findById() {
        val article = Article(
            title = "title",
            type = ArticleType.NEW,
            authorId = 1L,
            folderId = 1L,
            isPublic = true,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            content = "this is my content"
        )

        val saved = articleService.create(article)
        val found = articleService.findById(2L) ?: throw Exception("something went wrong")

        assertThat(saved).isEqualTo(found)
    }

    @Test
    fun findAllByIds() {
    }

    @Test
    fun deleteById() {
        articleService.deleteById(1L)
        assertNull(articleService.findById(1L))
    }

    @Test
    fun rollback() {
        val saved = articleService.findById(1L) ?: throw Exception("something went wrong")
        println(saved)
        saved.changeContent("second version")

        val updated = articleService.update(saved)
        assertNotNull(updated.currentVersion)
        assertThat(updated.currentVersion).isEqualTo(2L)
        println(updated)

        val rollbacked = articleService.rollbackTo(updated, 1L)
        assertNotNull(rollbacked.currentVersion)
        assertThat(rollbacked.currentVersion).isEqualTo(1L)
        println(rollbacked)
        rollbacked.changeContent("third version")

        val doubleUpdated = articleService.update(rollbacked)
        assertNotNull(doubleUpdated.currentVersion)
        assertThat(doubleUpdated.currentVersion).isEqualTo(3L)
        println(doubleUpdated)

        val currentPrevId = historyService.findById(doubleUpdated.currentVersion!!)!!.previousVersionId
        assertNotNull(currentPrevId)

        val currentPrev = historyService.findById(currentPrevId!!)
        assertNotNull(currentPrev?.id)
        assertThat(currentPrev?.id).isEqualTo(1L)
    }

    @Test
    fun treebuildTest() {
        fun roll(version: Long) : Long {
            val saved= articleService.findById( 1L) ?: throw Exception("something went wrong")
            saved.changeContent("updated $version")
            val updated = articleService.update(saved)
            val rollbacked = articleService.rollbackTo(updated, version)
            rollbacked.changeContent("updated after rollback $version")

            return articleService.update(rollbacked).currentVersion!!
        }

        val depth = 3;

        fun build(current: Long, currentDepth: Int) {
            if (currentDepth >= depth) return

            val leftVer = roll(current)
            val rightVer = roll(current)

            build(leftVer, currentDepth + 1)
            build(rightVer, currentDepth + 1)
        }

        build(1L, 0)
    }
}