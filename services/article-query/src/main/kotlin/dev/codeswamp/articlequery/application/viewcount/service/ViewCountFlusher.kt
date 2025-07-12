package dev.codeswamp.articlequery.application.viewcount.service

interface ViewCountFlusher {
    suspend fun flush()
}