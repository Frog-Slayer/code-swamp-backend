package dev.codeswamp.core.article.application.base

import dev.codeswamp.core.article.domain.article.model.Version

interface BaseSelectionPolicy {
    fun shouldStoreAsBase(version: Version): Boolean
}