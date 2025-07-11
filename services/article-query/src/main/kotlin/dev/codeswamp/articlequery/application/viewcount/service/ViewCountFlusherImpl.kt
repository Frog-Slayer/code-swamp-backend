package dev.codeswamp.articlequery.application.viewcount.service

import dev.codeswamp.articlequery.application.event.ViewCountFlushedEvent
import dev.codeswamp.articlequery.application.viewcount.ViewCountRepository
import dev.codeswamp.core.application.event.eventbus.EventPublisher
import dev.codeswamp.core.infrastructure.persistence.TransactionExecutor
import dev.codeswamp.framework.application.outbox.EventRecorder
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ViewCountFlusherImpl(
    @Qualifier("viewCountRedisTemplate") private val redis: ReactiveRedisOperations<String, String>,
    private val viewCountRepository: ViewCountRepository,
    private val eventRecorder: EventRecorder,
    private val transactionExecutor: TransactionExecutor
): ViewCountFlusher {
    private val prefix = "view:article:"

    @Scheduled(fixedRate = 600000)
    override suspend fun flush() {
        val keys = redis.keys("$prefix:*").collectList().awaitSingle()

        val viewCounts = keys.mapNotNull { key ->
            val articleId = key.removePrefix(prefix).toLongOrNull() ?: return@mapNotNull null
            val count = redis.opsForZSet().size(key).awaitFirstOrNull() ?: 0
            Pair(articleId, count.toInt())
        }.filter { it.second > 0 }

        val batches = viewCounts.chunked(100)

        transactionExecutor.execute {
            batches.forEach { chunk ->
                eventRecorder.record(ViewCountFlushedEvent(
                    chunk
                ))
            }
        }

        keys.forEach { key ->
            val articleId = key.removePrefix(prefix)
                .toLongOrNull()
                ?:  return@forEach

            viewCountRepository.clearViewers(articleId)
        }
    }
}