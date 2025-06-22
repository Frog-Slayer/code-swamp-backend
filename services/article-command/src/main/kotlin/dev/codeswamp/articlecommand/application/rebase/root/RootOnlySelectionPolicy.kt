package dev.codeswamp.articlecommand.application.rebase.root

import dev.codeswamp.articlecommand.application.rebase.RebasePolicy
import dev.codeswamp.articlecommand.domain.article.model.Version

class RootOnlySelectionPolicy : RebasePolicy {
    override suspend fun shouldStoreAsBase(version: Version): Boolean {
        return version.previousVersionId == null
    }
}