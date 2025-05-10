package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleType
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
    }

}