package dev.codeswamp.articleprojection.application.event.outbox.outbox

import dev.codeswamp.core.application.event.outbox.OutboxEventRepository
import dev.codeswamp.core.application.event.outbox.OutboxProcessor
import dev.codeswamp.core.application.event.outbox.ProcessResult
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
class OutboxProcessorImpl (
    private val outboxEventRepository : OutboxEventRepository,
    private val outboxEventPublisher: OutboxEventPublisher,
    private val transactionalOperator : TransactionalOperator,
) : OutboxProcessor {
    private val MAX_RETRY_ATTEMPTS = 5

    private val logger = LoggerFactory.getLogger(javaClass)
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @PostConstruct
    override fun startProcessing() {
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
    override fun stopProcessing() {
        scope.cancel("Application shutting down")
    }

    override suspend fun processOutbox() = coroutineScope {
        val events = outboxEventRepository.findPending(100)

        events.map {  outboxEvent ->
            async {
                 try {
                     outboxEventPublisher.publish(outboxEvent)
                     transactionalOperator.executeAndAwait {
                         outboxEventRepository.markAsSent(outboxEvent.id)
                     }

                     ProcessResult.Success(outboxEvent.id)
                 } catch (e : TransientEventException) {//maybe retriable
                      transactionalOperator.executeAndAwait {
                          val updatedCount = outboxEventRepository.incrementRetryCount(outboxEvent.id)
                          if (updatedCount >= MAX_RETRY_ATTEMPTS) {
                              outboxEventRepository.markAsSent(outboxEvent.id)
                              logger.warn("Event ${outboxEvent.id} reached max retry count. Mark as Failed", e)
                          } else {
                              logger.warn("Event ${outboxEvent.id} failed. Retry", e)
                          }
                     }

                     ProcessResult.Retry(outboxEvent.id, outboxEvent.retryCount)
                 }
                 catch (e : Exception) {//else
                    logger.warn("Exception in OutboxProcessor", e)
                    transactionalOperator.executeAndAwait {
                        outboxEventRepository.markAsFailed(outboxEvent.id)
                    }

                     ProcessResult.Failed(outboxEvent.id, e)
                }
            }
       }.awaitAll()
    }
}