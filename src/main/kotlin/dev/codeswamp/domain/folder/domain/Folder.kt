package dev.codeswamp.domain.folder.domain

import dev.codeswamp.domain.article.domain.model.Article
import dev.codeswamp.domain.user.entity.User
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

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