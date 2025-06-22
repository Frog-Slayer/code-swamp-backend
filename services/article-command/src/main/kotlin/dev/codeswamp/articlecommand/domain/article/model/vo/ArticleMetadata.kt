package dev.codeswamp.articlecommand.domain.article.model.vo

data class ArticleMetadata(
//가변 메타데이터
    val folderId: Long,
    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic: Boolean,


    /**
     * title & slug:
     * - publish된 이후에는 반드시 non-null, non-empty여야 한다.
     * - slug는 식별자로 사용하며, 각 폴더 내에서 유일해야 한다.
     */
    val slug: Slug?,
)