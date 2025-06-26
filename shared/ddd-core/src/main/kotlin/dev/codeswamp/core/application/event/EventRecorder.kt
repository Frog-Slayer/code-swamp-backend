package dev.codeswamp.core.application.event

import dev.codeswamp.core.common.event.BusinessEvent

interface EventRecorder {
    suspend fun record(event: BusinessEvent)
    suspend fun recordAll(events: List<BusinessEvent>)
}