package dev.codeswamp.articlequery.application.viewcount

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.EventType

@EventType("view-count.flushed")
data class ViewCountFlushedEvent(
    val pairs : List< Pair<Long, Int>>
) : ApplicationEvent