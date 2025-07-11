package dev.codeswamp.articlequery.infrastructure.persistence.redis

import dev.codeswamp.articlequery.application.context.Viewer
import dev.codeswamp.articlequery.application.viewcount.ViewCountRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import java.security.MessageDigest

@Repository
class ViewCountRepositoryImpl(
    @Qualifier("viewCountRedisTemplate") private val redis : ReactiveRedisTemplate<String, String>,
) : ViewCountRepository {

    fun Viewer.redisKey(): String {
        return userId?.let { "user:$userId" }
            ?: "anon:${"${ipAddress}:${userAgent}".sha256()}"
    }

    fun String.sha256(): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
        return bytes.joinToString("") { byte -> "%02x".format(byte) }
    }

    fun key(articleId: Long) = "view:article:$articleId"

    override suspend fun existsByArticleIdAndViewer(
        articleId: Long,
        viewer: Viewer
    ): Boolean {
        val viewerId = viewer.redisKey()
        val score = redis.opsForZSet()
            .score(key(articleId), viewerId)
            .awaitFirstOrNull()

        return score != null
    }

    override suspend fun increment(articleId: Long, viewer: Viewer) {
        val viewerId = viewer.redisKey()

        redis.opsForZSet()
            .add(key(articleId), viewerId, System.currentTimeMillis().toDouble())
            .awaitSingle()
    }

    override suspend fun getTotalViews(articleId: Long): Int {
        return redis.opsForZSet().size(key(articleId)).awaitSingle().toInt()
    }

    override suspend fun getAllTotalViewsOfArticles(articleIds: List<Long>): List<Pair<Long, Int>> {
        val ops = redis.opsForZSet()
        return articleIds.map{ articleId ->
            val count = ops.size(key(articleId)).awaitFirstOrNull()?.toInt() ?: 0
            articleId to count
        }
    }

    override suspend fun clearViewers(articleId: Long) {
        redis.opsForZSet().delete(key(articleId)).awaitSingle()
    }
}