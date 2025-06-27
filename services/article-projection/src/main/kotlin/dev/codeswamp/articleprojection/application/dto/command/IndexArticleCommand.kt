package dev.codeswamp.articleprojection.application.dto.command

data class IndexArticleCommand(
    val articleId: Long,
    val authorId: Long,
    val title: String,
    val preprocessedText: String,
    val isPublic: Boolean,
) {
    companion object {
        fun from(versionedArticle: VersionedArticle, preprocessedText: String): IndexArticleCommand {
            return IndexArticleCommand(
                articleId = versionedArticle.id,
                authorId = versionedArticle.authorId,
                title = requireNotNull(versionedArticle.currentVersion.title).value,
                preprocessedText = preprocessedText,
                isPublic = versionedArticle.metadata.isPublic,
            )
        }
    }
}