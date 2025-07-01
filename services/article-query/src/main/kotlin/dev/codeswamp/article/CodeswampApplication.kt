package dev.codeswamp.article

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class CodeswampApplication

fun main(args: Array<String>) {
    runApplication<CodeswampApplication>(*args)
}
