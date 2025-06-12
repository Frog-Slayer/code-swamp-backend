package dev.codeswamp.core.article.infrastructure.support

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class FlexMarkdownPreprocessorTest {

    @Test
    fun parseTest() {
        val markdown = """
        # header1
        ## header2
        ### header3
        #### header4
        ##### header5
        *italic*
        [text](url)
        ![text](imageurl)
        pure 
        > quote
        ``` java
        code block
        ```
        1. ordered1
        2. ordered2
        - unordered-
        * unordered*
        **bold**
    """.trimIndent()

        val pureText = FlexMarkdownPreprocessor.preprocess(markdown)
        println(pureText)
    }
}