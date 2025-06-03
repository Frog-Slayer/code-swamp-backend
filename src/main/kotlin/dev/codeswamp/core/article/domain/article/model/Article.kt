package dev.codeswamp.core.article.domain.article.model

import org.springframework.security.access.AccessDeniedException
import java.time.Instant

data class Article (
    /**
     * Metadata: 메타 데이터 변경은 버전 관리에 포함되지 않는다.
     */
    val id: Long,
    val authorId: Long,
    val folderId: Long,
    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic : Boolean,

    /**
     * isPublished: 한 번 publish된 이후에는 true를 유지.
     * Draft 상태에서는 false, publish 후에는 변경 금지.
     */
    val isPublished : Boolean,//한번 publish된 이후에는 true를 유지

    /**
     * title & slug:
     * - publish된 이후에는 반드시 non-empty여야 하며, 각 폴더 내에서 유일해야 한다.
     * - slug는 식별자로 사용됨.
     */
    val title: String? = null,
    val slug: String? = null,


    /**
     * createdAt: 문서 최초 생성 시점
     * updatedAt은 currentVersion의 createdAt과 동일하다.
     */
    val createdAt: Instant = Instant.now(),


    /**
     * Content 및 버전 관리:
     * - content(raw Markdown document)는 오직 변경 시에만 새로운 Version 생성됨.
     * - currentVersion은 현재 버전
     */
    val content: String,
    val currentVersion: Version
) {
    fun updateMetadata(
        folderId: Long? = null ,
        summary: String? = null,
        thumbnailUrl: String? = null,
        isPublic : Boolean? = null,
        title: String? = null,
        slug: String? = null,
    ) : Article {
        TODO()
    }

    fun updateContent(content: String? = null) : Article {
        TODO()
    }



    fun checkOwnership(userId: Long) {
        if (authorId != userId) throw AccessDeniedException("You are not the owner of this article")
    }

    fun assertReadableBy(userId: Long?) {
        if (!isPublic && (userId == null || authorId != userId))
            throw AccessDeniedException("cannot read private article")
    }
}
