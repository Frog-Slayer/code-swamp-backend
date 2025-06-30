package dev.codeswamp

import dev.codeswamp.framework.infrastructure.config.FrameworkBeansConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import

@SpringBootApplication
@EnableAspectJAutoProxy
@Import(FrameworkBeansConfig::class)
class ArticleCommandApplication

fun main(args: Array<String>) {
    runApplication<ArticleCommandApplication>(*args)
}
