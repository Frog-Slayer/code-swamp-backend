package dev.codeswamp.core.application.event.eventbus

import dev.codeswamp.core.common.event.BusinessEvent

abstract class EventKeyResolver {
    abstract fun resolveKey(event: BusinessEvent) : String
}