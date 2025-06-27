package dev.codeswamp

import dev.codeswamp.infrakafka.KafkaConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import

@SpringBootApplication
@EnableAspectJAutoProxy
@Import(KafkaConfig::class)
class ArticleCommandApplication

fun main(args: Array<String>) {
    runApplication<ArticleCommandApplication>(*args)
}
