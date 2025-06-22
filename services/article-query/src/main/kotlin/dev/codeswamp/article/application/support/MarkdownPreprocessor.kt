package dev.codeswamp.article.application.support

interface MarkdownPreprocessor {
    fun preprocess(rawMarkdown: String): String
}