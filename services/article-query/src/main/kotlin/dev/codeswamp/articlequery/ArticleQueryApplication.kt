package dev.codeswamp.articlequery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
class ArticleQueryApplication

fun main(args: Array<String>) {
    runApplication<ArticleQueryApplication>(*args)
}
