package dev.codeswamp.articlecommand.application.event.outbox

import dev.codeswamp.core.common.event.Event
import java.util.concurrent.ConcurrentHashMap

object EventTypeRegistry {
    private val registry: MutableMap<String, Class<out Event>> = ConcurrentHashMap()

    fun register(event: Event) : String {
        val clazz = event::class.java
        val className = clazz.name

        registry.putIfAbsent(className, clazz)?. let{
            if ( it != clazz) {
                throw IllegalArgumentException("Another event type of typename $className already registered")
            }
        }

        return className
    }

    fun getClassFor(typeName: String): Class<out Event> =
        registry[typeName] ?: throw IllegalArgumentException("Event type $typeName not registered")
}