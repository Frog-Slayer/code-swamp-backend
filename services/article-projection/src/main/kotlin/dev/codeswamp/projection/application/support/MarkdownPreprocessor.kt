package dev.codeswamp.projection.application.support

interface MarkdownPreprocessor {
    fun preprocess(rawMarkdown: String): String
}