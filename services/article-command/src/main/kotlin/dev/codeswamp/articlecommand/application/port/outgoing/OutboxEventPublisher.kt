package dev.codeswamp.articlecommand.application.port.outgoing

import dev.codeswamp.core.application.event.EventPublisher
import dev.codeswamp.core.application.event.outbox.OutboxEvent

interface OutboxEventPublisher : EventPublisher<OutboxEvent>