package dev.codeswamp.user.domain.user.event

import dev.codeswamp.core.common.EventType
import dev.codeswamp.core.domain.DomainEvent

@EventType("user.registered")
data class UserRegisteredEvent(
    val userId: Long,
    val username: String,
) : DomainEvent