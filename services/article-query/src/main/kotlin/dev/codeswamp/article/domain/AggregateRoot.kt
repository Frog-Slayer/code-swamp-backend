package dev.codeswamp.article.domain

abstract class AggregateRoot {
    private val domainEvents = mutableListOf<ArticleDomainEvent>()

    protected fun addEvent(event: ArticleDomainEvent) {
        domainEvents.add(event)
    }

    fun pullEvents(): List<ArticleDomainEvent> {
        val events = domainEvents.toList()
        domainEvents.clear()
        return events
    }
}