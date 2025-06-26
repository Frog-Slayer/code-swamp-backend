package dev.codeswamp.core.article.domain.article.model

import dev.codeswamp.articlecommand.domain.article.event.ArticlePublishedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticleVersionCreatedEvent
import dev.codeswamp.articlecommand.domain.article.model.VersionState
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.Instant

class VersionedArticleTest {

    private val id = 1L;
    private val authorId = 100L
    private val createdAt = Instant.now()

    private val metadata = ArticleMetadata(
        folderId = 10L,
        slug = Slug.of("test-slug"),
        summary = "test",
        thumbnailUrl = "",
        isPublic = true,
    )

    @Test
    fun `문서 생성 테스트`() {
        val article = VersionedArticle.create(
            id = id,
            authorId = authorId,
            createdAt = createdAt,
            metadata = metadata,
            diff = "full-content",
            fullContent = "full-content",
            versionId = 1L,
            title = "title"
        )

        assertThat(article.currentVersion.id).isEqualTo(1L)
        assertThat(article.currentVersion.isBaseVersion).isTrue()
        assertThat(article.currentVersion.fullContent).isEqualTo("full-content")
    }

    private fun baseArticle() = VersionedArticle.create(
        id = id,
        authorId = authorId,
        createdAt = createdAt,
        metadata = metadata,
        diff = "full-content",
        fullContent = "full-content",
        versionId = 1L,
        title = "title"
    )

    @Test
    fun `diff가 없고 title 변화가 없으면 기존 version을 반환`() : Unit = runBlocking {
        val article = baseArticle()

        val updated = article.updateVersionIfChanged(
            diff = "", title = "title", generateId = { 2L }, createdAt = Instant.now(),
            shouldRebase = { version -> false },
            reconstructFullContent = { version -> "" }
        )

        assertThat(updated).isEqualTo(article)
    }

    @Test
    fun `diff가 있으면 새 version을 반환`() : Unit = runBlocking {
        val article = baseArticle()

        val updated = article.updateVersionIfChanged(
            diff = "+++updated", title = "title", generateId = { 2L }, createdAt = Instant.now(),
            shouldRebase = { version -> false },
            reconstructFullContent = { version -> "" }
        )

        assertThat(updated).isNotEqualTo(article)
        assertThat(updated.currentVersion.diff).isEqualTo("+++updated")
        assertThat(updated.currentVersion.id).isEqualTo(2L)

        assertThat(updated.pullEvents().first()).isInstanceOf(ArticleVersionCreatedEvent::class.java)
    }

    @Test
    fun `title 변화 시 version을 반환`(): Unit = runBlocking {
        val article = baseArticle()

        val updated = article.updateVersionIfChanged(
            diff = "", title = "title2", generateId = { 2L }, createdAt = Instant.now(),
            shouldRebase = { version -> false },
            reconstructFullContent = { version -> "" }
        )

        assertThat(updated).isNotEqualTo(article)
        assertThat(updated.currentVersion.title?.value).isEqualTo("title2")
        assertThat(updated.currentVersion.id).isEqualTo(2L)

        assertThat(updated.pullEvents().first()).isInstanceOf(ArticleVersionCreatedEvent::class.java)
    }

    @Test
    fun `publish 시 slug 중복 검사 후 published 상태로 변경하고 이벤트를 추가`() : Unit = runBlocking {
        val article = baseArticle()
        val checker = { _: VersionedArticle, _: Long, _: Slug -> }

        val published = article.publish(checker)

        assertThat(published.isPublished).isTrue()
        assertThat(published.currentVersion.state).isEqualTo(VersionState.PUBLISHED)

        assertThat(published.pullEvents().first()).isInstanceOf(ArticlePublishedEvent::class.java)
    }


    @Test
    fun `published 상태에서 archive 시 예외 발생`(): Unit = runBlocking{
        val article = baseArticle()
        val checker = { _: VersionedArticle, _: Long, _: Slug -> }
        val published = article.publish(checker)

        assertThatThrownBy { published.archive() }
            .isInstanceOf(IllegalStateException::class.java)
    }


}
