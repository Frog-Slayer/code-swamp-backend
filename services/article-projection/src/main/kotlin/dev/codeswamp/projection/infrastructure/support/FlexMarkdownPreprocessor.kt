package dev.codeswamp.projection.infrastructure.support

import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.ast.TextCollectingVisitor
import dev.codeswamp.projection.application.support.MarkdownPreprocessor
import org.springframework.stereotype.Component

@Component
object FlexMarkdownPreprocessor : MarkdownPreprocessor {
    override fun preprocess(rawMarkdown: String): String {
        val parser = Parser.builder().build()
        val document: Node = parser.parse(rawMarkdown)

        return TextCollectingVisitor().collectAndGetText(document)//pure text
    }
}