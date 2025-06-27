package dev.codeswamp.framework.application.port.incoming

import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.application.event.eventbus.EventDispatcher
import dev.codeswamp.core.common.event.BusinessEvent

class BusinessEventDispatcher (
    private val handlers: List<EventHandler<*>>
) : EventDispatcher<BusinessEvent> {

    override suspend fun dispatch(event: BusinessEvent) {
        handlers.filter { it.canHandle(event) }
            .forEach { ( it as EventHandler<BusinessEvent> ).handle(event) }
    }
}