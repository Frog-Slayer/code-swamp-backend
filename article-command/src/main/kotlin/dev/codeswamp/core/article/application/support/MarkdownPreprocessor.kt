package dev.codeswamp.core.article.application.support

interface MarkdownPreprocessor {
    fun preprocess(rawMarkdown: String): String
}