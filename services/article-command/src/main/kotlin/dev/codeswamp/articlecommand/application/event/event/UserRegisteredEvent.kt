package dev.codeswamp.articlecommand.application.event.event

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.EventType

@EventType("user.registered")
data class UserRegisteredEvent(
    val userId: Long,
    val username: String,
) : ApplicationEvent