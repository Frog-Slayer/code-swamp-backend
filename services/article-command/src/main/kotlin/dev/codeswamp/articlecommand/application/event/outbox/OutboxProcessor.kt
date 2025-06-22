package dev.codeswamp.articlecommand.application.event.outbox

import dev.codeswamp.articlecommand.application.port.outgoing.InternalEventPublisher
import dev.codeswamp.articlecommand.infrastructure.exception.infrastructure.EventTransientException
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.core.common.event.EventPublisher
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CancellationException
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
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@Component
class OutboxProcessor (
    private val outboxEventRepository : OutboxEventRepository,
    private val internalEventPublisher: InternalEventPublisher,
    private val transactionalOperator : TransactionalOperator,
) {
    private val MAX_RETRY_ATTEMPTS = 5

    private val logger = LoggerFactory.getLogger(javaClass)
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @PostConstruct
    fun afterPropertiesSet() {
        scope.launch {
            try {
                while (isActive) {
                    processOutbox()
                    delay(5000)
                }
            } catch (e: CancellationException) {
                logger.info("OutboxProcessor coroutine cancelled", e)
            } catch (e : Exception) {
                logger.error("Unexpected exception in OutboxProcessor", e)
            }
        }
    }

    @PreDestroy
     fun destroy() {
        scope.cancel("Application shutting down")
    }

    suspend fun processOutbox() = coroutineScope {
        val events = outboxEventRepository.findPending(100)

        events.map {  outboxEvent ->
            async {
                 try {
                     internalEventPublisher.publish(outboxEvent.payload)
                     transactionalOperator.executeAndAwait {
                         outboxEventRepository.markAsSent(outboxEvent.id)
                     }
                     logger.info("Event ${outboxEvent.id} sent")
                 } catch (e : EventTransientException) {//maybe retriable
                      transactionalOperator.executeAndAwait {
                          val updatedCount = outboxEventRepository.incrementRetryCount(outboxEvent.id)
                          if (updatedCount >= MAX_RETRY_ATTEMPTS) {
                              outboxEventRepository.markAsSent(outboxEvent.id)
                              logger.warn("Event ${outboxEvent.id} reached max retry count. Mark as Failed", e)
                          } else {
                              logger.warn("Event ${outboxEvent.id} failed. Retry", e)
                          }
                     }
                 }
                 catch (e : Exception) {//else
                    logger.warn("Exception in OutboxProcessor", e)
                    transactionalOperator.executeAndAwait {
                        outboxEventRepository.markAsFailed(outboxEvent.id)
                    }
                }
            }
       }.awaitAll()
    }
}