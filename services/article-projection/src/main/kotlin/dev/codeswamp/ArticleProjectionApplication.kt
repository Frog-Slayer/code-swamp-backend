package dev.codeswamp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class ArticleProjectionApplication

fun main(args: Array<String>) {
    runApplication<ArticleProjectionApplication>(*args)
}
