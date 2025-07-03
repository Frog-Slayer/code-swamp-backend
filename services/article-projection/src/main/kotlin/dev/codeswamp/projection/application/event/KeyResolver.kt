package dev.codeswamp.projection.application.event

import dev.codeswamp.core.application.event.eventbus.EventKeyResolver
import dev.codeswamp.core.common.event.BusinessEvent
import org.springframework.stereotype.Component

@Component
class KeyResolver : EventKeyResolver {
    override fun resolveKey(event: BusinessEvent): String {
        return "key"
    }
}