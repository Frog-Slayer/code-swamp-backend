package dev.codeswamp.core.article.infrastructure.persistence.jpar

import dev.codeswamp.core.article.domain.article.model.Article
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.ArticleJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@DisplayName("ArticleRepositoryImpl test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ArticleRepositoryImplTest (
    @Autowired private val articleEntityRepository: ArticleJpaRepository,
    @Autowired private val versionJpaRepository: VersionJpaRepository,
    @Autowired private val articleRepository: ArticleRepository
){
    @BeforeAll
    fun beforeAll() {
        val article = Article(
            title = "test",
            authorId = 1 ,
            folderId = 1,
            content = "hello"
        )

        articleRepository.save(article)
    }

    @Test
    fun `저장 후 조회 테스트`() {
        val found = articleRepository.findById(1)

        assertNotNull(found)
        assertThat(found?.id!!).isEqualTo(1)
    }

    @Test
    @Order(1)
    fun `컨텐츠 수정 X 테스트`() {

    }

    @Test
    @Order(2)
    fun `컨텐츠 수정 테스트`() {

    }

    @Test
    fun delete() {
    }

    @Test
    fun findAllByIds() {
    }

    @Test
    fun findById() {
    }
}