package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.VersionState
import dev.codeswamp.core.article.domain.article.service.ArticleDomainService
import dev.codeswamp.core.article.domain.article.service.VersionService
import dev.codeswamp.core.article.infrastructure.persistence.graph.repository.VersionNodeRepository
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
    @Autowired private val versionNodeRepository: VersionNodeRepository,
    @Autowired private val historyService: VersionService,
    @Autowired private val articleDomainService: ArticleDomainService,
){

    @BeforeAll
    fun create() {
        val versionedArticle = VersionedArticle(
            title = "title",
            status = VersionState.NEW,
            authorId = 1L,
            folderId = 1L,
            isPublic = true,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            content = "this is my content",
            summary = TODO(),
            thumbnailUrl = TODO(),
            slug = TODO(),
            currentVersion = TODO()
        )

        val saved = articleDomainService.create(versionedArticle)

        assertThat(saved).isNotNull()
        assertThat(saved.title).isEqualTo("title")
        assertThat(saved.id).isEqualTo(1L)
    }

    @Test
    fun contentUpdateTest() {
        val article = articleDomainService.findById(1L) ?: throw Exception("something went wrong")

        val updateString = "this content is updated"
        article.content = updateString

        val saved = articleDomainService.update(article)

        assertThat(saved).isNotNull()
        assertThat(saved.content).isEqualTo(updateString)
        assertThat(saved.id).isEqualTo(1L)

        val currentVersion = saved.currentVersion?:throw Exception("something went wrong")
        val node = versionNodeRepository.findByVersionId(currentVersion)

        assertThat(node?.previous).isNotNull()
    }

    @Test
    fun metadataChangeTest() {
        val article = articleDomainService.findById(1L)?.copy(
            title = "title22",
            status = VersionState.PUBLISHED,
            authorId = 1L,
            folderId = 1L,
            isPublic = false,
        ) ?: throw Exception("something went wrong")

        val saved = articleDomainService.update(article)

        assertThat(saved).isNotNull()
        assertThat(saved.id).isEqualTo(1L)
        assertThat(saved.title).isEqualTo("title22")
        assertThat(saved.currentVersion).isEqualTo(1L)
    }

    @Test
    fun findById() {
        val versionedArticle = VersionedArticle(
            title = "title",
            status = VersionState.NEW,
            authorId = 1L,
            folderId = 1L,
            isPublic = true,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            content = "this is my content"
        )

        val saved = articleDomainService.create(versionedArticle)
        val found = articleDomainService.findById(2L) ?: throw Exception("something went wrong")

        assertThat(saved).isEqualTo(found)
    }

    @Test
    fun deleteById() {
        articleDomainService.deleteById(1L)
        assertNull(articleDomainService.findById(1L))
    }

    @Test
    fun rollback() {
        val saved = articleDomainService.findById(1L) ?: throw Exception("something went wrong")
        println(saved)
        saved.changeContent("second version")

        val updated = articleDomainService.update(saved)
        assertNotNull(updated.currentVersion)
        assertThat(updated.currentVersion).isEqualTo(2L)
        println(updated)

        val rollbacked = articleDomainService.rollbackTo(updated, 1L)
        assertNotNull(rollbacked.currentVersion)
        assertThat(rollbacked.currentVersion).isEqualTo(1L)
        println(rollbacked)
        rollbacked.changeContent("third version")

        val doubleUpdated = articleDomainService.update(rollbacked)
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
            val saved= articleDomainService.findById( 1L) ?: throw Exception("something went wrong")
            saved.changeContent("updated $version")
            val updated = articleDomainService.update(saved)
            val rollbacked = articleDomainService.rollbackTo(updated, version)
            rollbacked.changeContent("updated after rollback $version")

            return articleDomainService.update(rollbacked).currentVersion!!
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