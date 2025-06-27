package dev.codeswamp.core.application.port.outgoing

import dev.codeswamp.core.application.event.eventbus.EventPublisher
import dev.codeswamp.core.application.event.outbox.OutboxEvent

interface OutboxEventPublisher : EventPublisher<OutboxEvent>