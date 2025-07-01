package dev.codeswamp.user.application.event

import dev.codeswamp.core.application.event.eventbus.EventKeyResolver
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.user.domain.user.event.UserRegisteredEvent
import org.springframework.stereotype.Component

@Component
class KeyResolver : EventKeyResolver{
    override fun resolveKey(event: BusinessEvent): String {
        return when (event) {
            is AuthUserRollbackRequestedEvent-> "user-registered-${event.email}"
            is UserRegisteredEvent -> "user-registered-${event.userId}"
            else -> throw IllegalArgumentException("Unsupported BusinessEvent: $event")
        }
    }
}