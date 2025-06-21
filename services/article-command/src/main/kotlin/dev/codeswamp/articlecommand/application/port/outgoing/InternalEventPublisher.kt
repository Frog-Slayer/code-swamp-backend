package dev.codeswamp.articlecommand.application.port.outgoing

import dev.codeswamp.core.common.event.Event
import dev.codeswamp.core.common.event.EventPublisher

interface InternalEventPublisher : EventPublisher<Event>