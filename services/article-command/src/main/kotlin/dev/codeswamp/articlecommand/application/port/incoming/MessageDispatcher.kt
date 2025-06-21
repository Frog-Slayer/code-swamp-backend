package dev.codeswamp.articlecommand.application.port.incoming

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.event.EventHandler
import org.springframework.stereotype.Component

@Component
class MessageDispatcher(
    private val handlers: List<EventHandler<ApplicationEvent>>
) {
    suspend fun dispatch(event: ApplicationEvent) {
        handlers.filter { it.canHandle(event) }
            .forEach { it.handle(event) }
    }
}