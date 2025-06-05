package dev.codeswamp.core.article.infrastructure.search

import dev.codeswamp.core.article.application.dto.command.ArticleIndexDTO
import dev.codeswamp.core.article.application.dto.command.ArticleSearchDTO
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.infrastructure.persistence.search.config.MeiliSearchConfig
import dev.codeswamp.core.article.infrastructure.persistence.search.MeiliSearchIndexer
import dev.codeswamp.core.article.infrastructure.support.FlexMarkdownPreprocessor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeiliSearchIndexerTest {
    val meiliClient = MeiliSearchConfig().meiliWebClient()
    val indexer = MeiliSearchIndexer(meiliClient)

    @BeforeAll
    fun index() {
        val versionedArticle = VersionedArticle(
            id = 1,
            title = "test",
            authorId = 1 ,
            folderId = 1,
            content = "hello"
        )
        val preprocessedText : String = FlexMarkdownPreprocessor.preprocess(versionedArticle.content)
        val indexDTO = ArticleIndexDTO.from(versionedArticle, preprocessedText)

        indexer.index(indexDTO)
    }

    @Test
    fun delete() {
        indexer.remove(1L)
    }


    @Test
    fun search() {
        val searchDTO = ArticleSearchDTO(
            keyword = "test",
        )
        val result = indexer.search(searchDTO)
        println(result)

        assertThat(result).isNotNull()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0]).isEqualTo(1L)



    }
}