package dev.codeswamp.articlecommand.application.port.incoming

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.application.event.eventbus.EventDispatcher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ApplicationEventDispatcher(
    private val handlers: List<EventHandler<*>>
) : EventDispatcher<ApplicationEvent> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override suspend fun dispatch(event: ApplicationEvent) {
        logger.info("Event dispatched: $event")
        logger.info("Handlers: $handlers")
        handlers.filter { it.canHandle(event) }
            .forEach { ( it as EventHandler<ApplicationEvent> ).handle(event) }
    }
}