package dev.codeswamp.user.application.event

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.EventType

@EventType("user.register.rollback")
data class AuthUserRollbackRequestedEvent(
    val email: String
) : ApplicationEvent