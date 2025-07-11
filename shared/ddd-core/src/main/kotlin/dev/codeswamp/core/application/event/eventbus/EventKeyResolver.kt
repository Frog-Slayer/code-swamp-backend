package dev.codeswamp.core.application.event.eventbus

import dev.codeswamp.core.common.event.BusinessEvent

interface EventKeyResolver {
    fun resolveKey(event: BusinessEvent) : String?
}