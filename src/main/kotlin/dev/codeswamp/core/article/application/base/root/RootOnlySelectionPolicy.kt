package dev.codeswamp.core.article.application.base.root

import dev.codeswamp.core.article.application.base.BaseSelectionPolicy
import dev.codeswamp.core.article.domain.article.model.Version

class RootOnlySelectionPolicy : BaseSelectionPolicy {
    override fun shouldStoreAsBase(version: Version): Boolean {
        return version.previousVersionId == null
    }
}