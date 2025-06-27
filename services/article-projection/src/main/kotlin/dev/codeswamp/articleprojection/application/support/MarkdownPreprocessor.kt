package dev.codeswamp.articleprojection.application.support

interface MarkdownPreprocessor {
    fun preprocess(rawMarkdown: String): String
}