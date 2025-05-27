package dev.codeswamp.core.folder.domain.entity

data class Folder(
    val id: Long? = null,
    val ownerId: Long,//user?UserId?
    var name: String,
    var parentId: Long? = null,//root = null
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