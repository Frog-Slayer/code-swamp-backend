package dev.codeswamp.domain.folder.domain.entity

data class Folder(
    val id: Long? = null,
    val ownerId: Long,//user?UserId?
    private var name: String,
    private var parentId: Long? = null,
    val articles: MutableList<Long> = mutableListOf(),
) {
    fun rename(newName: String) {
        name = newName
    }

    fun addArticle(articleId: Long) {
        this.articles.add(articleId)
    }

    fun moveTo(newParentId: Long) {
        parentId = newParentId
    }
}