package dev.codeswamp.core.domain

abstract class AggregateRoot {
    private val domainEvents = mutableListOf<DomainEvent>()

    protected fun addEvent(event: DomainEvent) {
        domainEvents.add(event)
    }

    fun pullEvents(): List<DomainEvent> {
        val events = domainEvents.toList()
        domainEvents.clear()
        return events
    }
}