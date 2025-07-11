package dev.codeswamp.articlequery

import dev.codeswamp.framework.infrastructure.config.FrameworkBeansConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import

@Import(FrameworkBeansConfig::class)
@SpringBootApplication
class ArticleQueryApplication

fun main(args: Array<String>) {
    runApplication<ArticleQueryApplication>(*args)
}
