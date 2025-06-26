package dev.codeswamp.articlecommand.domain.article.model

data class VersionTreeDiff(
    val addedVersions: List<Version>,
    val changedVersions: List<Version>
)

data class VersionTree private constructor(
    val versions: Map<Long, Version>
) {
    companion object {
        // 새 버전 생성하는 것 필요(Version만 들어옴)
        fun createWithInitialVersion(initialVersion: Version) : VersionTree {
            if (initialVersion.parentId != null) throw IllegalStateException("VersionTree can only be created after initial version")
            return VersionTree(mapOf(initialVersion.id to initialVersion))
        }

        fun of(versions : Map<Long, Version>) = VersionTree(
            versions = versions
        )
    }

    fun get(versionId: Long) = versions[versionId] ?: throw IllegalStateException("VersionTree can only be created after initial version")

    fun findDiffChainTo(versionId: Long) : List<String> {
        val diffChain : MutableList<String> = mutableListOf()

        var currentVersionId : Long? = versionId

        while (currentVersionId != null) {
            val currentVersion = versions[currentVersionId] ?:  throw IllegalStateException("Inconsistent version tree")
            diffChain.add(currentVersion.diff)
            currentVersionId = currentVersion.parentId
        }

        return diffChain.asReversed()
    }

    fun append(version : Version) : VersionTree {
        val parentId = version.parentId
            ?: throw IllegalStateException("VersionTree can only be created after parent version")

        val parentVersion = versions[parentId]
            ?: throw IllegalStateException("VersionTree can only be created after parent version")

        if (parentVersion.articleId!= version.articleId) throw IllegalStateException("Illegal append")

        return this.copy( versions = versions + (version.id to version))
    }

    fun diff(origin: VersionTree) : VersionTreeDiff {
        val added = versions.keys - origin.versions.keys
        val changed = versions.keys.intersect(origin.versions.keys)
            .filter {
                versions[it] != origin.versions[it]
            }

        return VersionTreeDiff(
            addedVersions = added.mapNotNull { versions[it] },
            changedVersions = changed.mapNotNull { versions[it] }
        )
    }

    fun publish(versionId: Long) : VersionTree {
        val toPublish = versions[versionId]
            ?: throw  IllegalStateException("Cannot find version with versionId $versionId")

        if (toPublish.state == VersionState.PUBLISHED) return this //변화가 없는 상태

        val newVersions = versions.mapValues { (id, version) ->
            when {
                id == toPublish.id -> version.publish()
                version.state == VersionState.PUBLISHED -> version.archive()
                id == toPublish.parentId && version.state != VersionState.ARCHIVED -> version.archive()
                else -> version
            }
        }

        return this.copy(versions = newVersions)
    }

    fun draft (versionId: Long) : VersionTree {
        val toDraft = versions[versionId]
            ?: throw  IllegalStateException("Cannot find version with versionId $versionId")

        if (toDraft.state == VersionState.DRAFT) return this //변화가 없는 상태

        val newVersions = versions.mapValues { (id, version) ->
            when {
                id == toDraft.id -> version.draft()
                id == toDraft.parentId && version.state == VersionState.DRAFT-> version.archive()
                else -> version
            }
        }

        return this.copy(versions = newVersions)
    }

    fun archive (versionId: Long) : VersionTree {
        val toArchive = versions[versionId]
            ?: throw  IllegalStateException("Cannot find version with versionId $versionId")

        if (toArchive.state == VersionState.ARCHIVED) return this
        if (toArchive.state == VersionState.PUBLISHED) throw IllegalStateException("Cannot archived published version")

        val newVersions = versions.mapValues { (id, version) ->
            when {
                id == toArchive.id -> version.archive()
                else -> version
            }
        }
        return this.copy(versions = newVersions)
    }
}
