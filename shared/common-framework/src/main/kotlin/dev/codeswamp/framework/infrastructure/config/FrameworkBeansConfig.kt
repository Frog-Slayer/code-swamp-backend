package dev.codeswamp.framework.infrastructure.config

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.core.application.SystemIdGenerator
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.application.event.eventbus.EventKeyResolver
import dev.codeswamp.core.domain.IdGenerator
import dev.codeswamp.core.infrastructure.messaging.messaging.kafka.publisher.KafkaOutboxEventPublisher
import dev.codeswamp.core.infrastructure.persistence.TransactionExecutor
import dev.codeswamp.framework.application.outbox.DefaultEventRecorder
import dev.codeswamp.framework.application.outbox.DefaultEventTypeRegistry
import dev.codeswamp.framework.application.outbox.DefaultOutboxProcessor
import dev.codeswamp.framework.application.outbox.EventRecorder
import dev.codeswamp.framework.application.outbox.EventTypeRegistry
import dev.codeswamp.framework.application.outbox.OutboxProcessor
import dev.codeswamp.framework.application.port.incoming.BusinessEventDispatcher
import dev.codeswamp.framework.application.port.outgoing.OutboxEventPublisher
import dev.codeswamp.framework.application.port.outgoing.OutboxEventRepository
import dev.codeswamp.framework.infrastructure.messaging.kafka.KafkaEventTranslator
import dev.codeswamp.framework.infrastructure.persistence.config.TransactionConfig
import dev.codeswamp.framework.infrastructure.persistence.r2dbc.OutboxEventMapper
import dev.codeswamp.framework.infrastructure.persistence.r2dbc.OutboxEventRepositoryImpl
import dev.codeswamp.framework.infrastructure.support.DefaultIdGenerator
import dev.codeswamp.framework.infrastructure.support.DefaultSystemIdGenerator
import dev.codeswamp.infrakafka.KafkaConfig
import dev.codeswamp.infrakafka.KafkaEventPublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
@Import(KafkaConfig::class, TransactionConfig::class)
class FrameworkBeansConfig(
    @Value("\${spring.application.name}") private val serviceName : String,
    private val objectMapper: ObjectMapper,
    private val kafkaPublisher: KafkaEventPublisher,
    private val eventKeyResolver: EventKeyResolver
) {

    @Bean
    @ConditionalOnMissingBean
    fun idGenerator() : IdGenerator = DefaultIdGenerator()

    @Bean
    @ConditionalOnMissingBean
    fun systemIdGenerator() : SystemIdGenerator = DefaultSystemIdGenerator()

    @Bean
    @ConditionalOnMissingBean
    fun eventTypeRegistry() : EventTypeRegistry = DefaultEventTypeRegistry()


    @Bean
    @ConditionalOnMissingBean
    fun outboxEventMapper(
        eventTypeRegistry: EventTypeRegistry,
        objectMapper: ObjectMapper
    ) : OutboxEventMapper = OutboxEventMapper(
        eventTypeRegistry = eventTypeRegistry,
        objectMapper = objectMapper
    )

    @Bean
    fun businessEventDispatcher(
        handlers: List<EventHandler<*>>,
    ) = BusinessEventDispatcher(
        handlers = handlers
    )


    @Bean
    @ConditionalOnMissingBean
    fun outboxEventRepository(
        databaseClient: DatabaseClient,
        outboxEventMapper: OutboxEventMapper
    ) : OutboxEventRepository = OutboxEventRepositoryImpl(
        databaseClient = databaseClient,
        mapper = outboxEventMapper
    )

    @Bean
    @ConditionalOnMissingBean
    fun eventRecorder(
        outboxEventRepository: OutboxEventRepository,
        systemIdGenerator: SystemIdGenerator,
        eventTypeRegistry: EventTypeRegistry,
    ) : EventRecorder = DefaultEventRecorder(
        outboxRepository = outboxEventRepository,
        serviceName = serviceName,
        eventKeyResolver = eventKeyResolver,
        systemIdGenerator = systemIdGenerator,
        eventTypeRegistry = eventTypeRegistry,
    )

    @Bean
    @ConditionalOnMissingBean
    fun outboxProcessor(
        outboxEventRepository: OutboxEventRepository,
        outboxEventPublisher : OutboxEventPublisher,
        transactionExecutor : TransactionExecutor
    ) : OutboxProcessor = DefaultOutboxProcessor(
        outboxEventRepository = outboxEventRepository,
        outboxEventPublisher = outboxEventPublisher,
        transactionExecutor = transactionExecutor
    )

    @Bean
    @ConditionalOnMissingBean
    fun kafkaEventTranslator(
        objectMapper: ObjectMapper,
        eventTypeRegistry: EventTypeRegistry,
    ) = KafkaEventTranslator(
        objectMapper = objectMapper,
        eventTypeRegistry = eventTypeRegistry
    )

    @Bean
    @ConditionalOnMissingBean
    fun outboxEventPublisher() : OutboxEventPublisher = KafkaOutboxEventPublisher(
        objectMapper = objectMapper,
        kafkaPublisher = kafkaPublisher
    )
}