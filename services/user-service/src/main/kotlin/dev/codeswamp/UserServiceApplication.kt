package dev.codeswamp

import dev.codeswamp.infrakafka.config.KafkaConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(KafkaConfig::class)
@EnableAspectJAutoProxy
class UserServiceApplication

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}
