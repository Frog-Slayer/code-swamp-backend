package blog.codeswamp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CodeswampApplication

fun main(args: Array<String>) {
	runApplication<CodeswampApplication>(*args)
}
