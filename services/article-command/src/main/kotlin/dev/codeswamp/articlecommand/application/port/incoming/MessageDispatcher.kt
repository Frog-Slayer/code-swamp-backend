package dev.codeswamp.articlecommand.application.port.incoming

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.event.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MessageDispatcher(
    private val handlers: List<EventHandler<*>>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun dispatch(event: ApplicationEvent) {
        logger.info("Event dispatched: $event")
        logger.info("Handlers: $handlers")
        handlers.filter { it.canHandle(event) }
            .forEach { ( it as EventHandler<ApplicationEvent> ).handle(event) }
    }
}