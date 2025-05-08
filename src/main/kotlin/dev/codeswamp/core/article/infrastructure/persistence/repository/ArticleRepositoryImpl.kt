package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleType
import dev.codeswamp.core.article.domain.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleContentEntity
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleMetadataEntity
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    val articleMetadataJpaRepository: ArticleMetadataJpaRepository,
    val articleContentJpaRepository: ArticleContentJpaRepository,
) : ArticleRepository {
    override fun save(article: Article): Article {
        return if (article.id == null) {
            val metadataEntity = articleMetadataJpaRepository.save(
                ArticleMetadataEntity(
                    title = article.title,
                    authorId = article.authorId,
                    folderId = article.folderId,
                    createdAt = article.createdAt,
                    updatedAt = article.updatedAt,
                    isPublic = article.isPublic,
                    currentVersion = null,
                )
            )

            val contentEntity = articleContentJpaRepository.save (
                ArticleContentEntity(
                    articleMetadataEntity = metadataEntity,
                    content = article.content,
                    createdAt = article.createdAt,
                )
            )

            metadataEntity.currentVersion =  contentEntity.id
            toDomain(metadataEntity, contentEntity)
        }
        else {
            val metadataEntity = articleMetadataJpaRepository.findById(article.id)
                .orElseThrow{ Exception("Could not find ${article.id}") }

            val currentContentEntity = articleContentJpaRepository.findById(metadataEntity.currentVersion!!)
                .orElseThrow{ Exception("Could not find any version of ${article.id}") }

            metadataEntity.title = article.title
            metadataEntity.updatedAt = article.updatedAt
            metadataEntity.isPublic = article.isPublic

            val finalContentEntity = if (currentContentEntity.content != article.content) {
                val newContentEntity = ArticleContentEntity(
                        articleMetadataEntity = metadataEntity,
                        content = article.content,
                        createdAt = article.updatedAt,
                    )

                metadataEntity.contentVersions.add(newContentEntity)
                metadataEntity.currentVersion = newContentEntity.id

                newContentEntity
            }
            else {
                currentContentEntity
            }

            toDomain(metadataEntity, finalContentEntity)
        }
    }

    override fun delete(article: Article) {
        if (article.id == null) { throw Exception("no such article") }//TODO

        val metadataEntity = articleMetadataJpaRepository.findById(article.id)
                .orElseThrow{ Exception("Could not find ${article.id}") }//TODO

        articleMetadataJpaRepository.deleteById(metadataEntity.id!!)
    }

    override fun findAllByIds(articleIds: List<Long>): List<Article> {
        val metadataEntities = articleMetadataJpaRepository.findAllById(articleIds)

        val contentEntities = articleContentJpaRepository.findAllById(
            metadataEntities.map {it.currentVersion}
        ).associateBy { it.id }

        return metadataEntities.map { metadataEntity ->
            val contentEntity = contentEntities[metadataEntity.currentVersion]
                ?: throw Exception("Cannot find content for article version")

            toDomain(metadataEntity, contentEntity)
        }
    }

    override fun findById(articleId: Long): Article? {
        val metadataEntity = articleMetadataJpaRepository.findById(articleId)
            .orElseThrow{Exception("Could not find article") }//TODO

        val contentEntity = articleContentJpaRepository.findById(metadataEntity.id!!)
            .orElseThrow{ Exception("Could not find article") }//TODO

        return toDomain(metadataEntity, contentEntity)
    }

    private fun toDomain(metadata: ArticleMetadataEntity, content: ArticleContentEntity): Article {
        return  Article(
            id = metadata.id,
            title = metadata.title,
            type = ArticleType.NEW,//TODO
            authorId = metadata.authorId,
            folderId = metadata.folderId,
            isPublic = metadata.isPublic,
            createdAt = metadata.createdAt,
            updatedAt = metadata.updatedAt,
            content = content.content
        )
    }
}