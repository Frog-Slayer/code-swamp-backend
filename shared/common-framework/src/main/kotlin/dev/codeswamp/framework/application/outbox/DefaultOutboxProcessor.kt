package dev.codeswamp.framework.application.outbox

import dev.codeswamp.core.infrastructure.persistence.TransactionExecutor
import dev.codeswamp.framework.application.port.outgoing.OutboxEventPublisher
import dev.codeswamp.framework.application.port.outgoing.OutboxEventRepository
import dev.codeswamp.framework.infrastructure.messaging.exception.TransientEventException
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import kotlin.coroutines.cancellation.CancellationException

class DefaultOutboxProcessor(
    private val outboxEventRepository : OutboxEventRepository,
    private val outboxEventPublisher: OutboxEventPublisher,
    private val transactionExecutor : TransactionExecutor
) : OutboxProcessor {
    private val MAX_RETRY_ATTEMPTS = 5
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val logger : Logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedRate = 5000)
    fun flushOutboxEvents() = runBlocking {
        processOutbox()
    }

    override suspend fun processOutbox() = coroutineScope {
        val events = outboxEventRepository.findPending(100)

        events.map { outboxEvent ->
            async {
                try {
                    outboxEventPublisher.publish(outboxEvent)
                    transactionExecutor.execute {
                        outboxEventRepository.markAsSent(outboxEvent.id)
                    }

                    ProcessResult.Success(outboxEvent.id)
                } catch (e: TransientEventException) {
                    transactionExecutor.execute {
                        val updatedCount = outboxEventRepository.incrementRetryCount(outboxEvent.id)
                        if (updatedCount >= MAX_RETRY_ATTEMPTS) {
                            outboxEventRepository.markAsFailed(outboxEvent.id)
                            logger.warn("Event ${outboxEvent.id} reached max retry count. Mark as Failed", e)
                        }
                    }

                    ProcessResult.Retry(outboxEvent.id, outboxEvent.retryCount)
                } catch (e: Exception) {//else
                    transactionExecutor.execute {
                        outboxEventRepository.markAsFailed(outboxEvent.id)
                    }

                    ProcessResult.Failed(outboxEvent.id, e)
                }
            }
        }.awaitAll()
    }
}