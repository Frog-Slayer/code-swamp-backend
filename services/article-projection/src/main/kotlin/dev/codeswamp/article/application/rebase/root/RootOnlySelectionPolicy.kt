package dev.codeswamp.article.application.rebase.root

import dev.codeswamp.article.application.rebase.RebasePolicy
import dev.codeswamp.article.domain.article.model.Version

class RootOnlySelectionPolicy : RebasePolicy {
    override fun shouldStoreAsBase(version: Version): Boolean {
        return version.previousVersionId == null
    }
}