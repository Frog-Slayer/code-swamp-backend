package dev.codeswamp.projection

import dev.codeswamp.framework.infrastructure.config.FrameworkBeansConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import

@SpringBootApplication
@EnableAspectJAutoProxy
@Import(FrameworkBeansConfig::class)
class ArticleProjectionApplication

fun main(args: Array<String>) {
    runApplication<ArticleProjectionApplication>(*args)
}
