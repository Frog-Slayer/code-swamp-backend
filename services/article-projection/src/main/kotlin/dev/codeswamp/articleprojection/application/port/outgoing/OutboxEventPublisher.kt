package dev.codeswamp.articleprojection.application.port.outgoing

import dev.codeswamp.core.application.event.eventbus.EventPublisher
import dev.codeswamp.core.infrastructure.messaging.outbox.OutboxEvent

interface OutboxEventPublisher : EventPublisher<OutboxEvent>