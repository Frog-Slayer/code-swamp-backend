package dev.codeswamp.infrakafka.config

import dev.codeswamp.infrakafka.event.KafkaEvent
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ContainerProperties

@Configuration
@EnableKafka
class KafkaConfig(
    private val kafkaProperties: KafkaProperties,
) {
    val logger = LoggerFactory.getLogger(javaClass)
    @PostConstruct
    fun init() {
        logger.warn("Kafka configuration: {}", kafkaProperties.producer.valueSerializer)
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, KafkaEvent> {
        val configs = kafkaProperties.buildProducerProperties()
        configs["spring.json.add.type.headers"] = false
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, KafkaEvent> = KafkaTemplate(producerFactory())

    @Bean
    fun consumerFactory(): ConsumerFactory<String, KafkaEvent> {
        val configs = kafkaProperties.buildConsumerProperties()
       return DefaultKafkaConsumerFactory(configs)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, KafkaEvent>()
        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }
}