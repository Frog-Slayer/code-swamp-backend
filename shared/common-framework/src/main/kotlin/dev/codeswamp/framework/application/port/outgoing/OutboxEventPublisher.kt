package dev.codeswamp.framework.application.port.outgoing

import dev.codeswamp.core.application.event.eventbus.EventPublisher
import dev.codeswamp.framework.application.outbox.OutboxEvent

interface OutboxEventPublisher : EventPublisher<OutboxEvent>