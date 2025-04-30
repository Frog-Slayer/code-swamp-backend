package dev.codeswamp.domain.article.entity.article

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
data class ArticleView(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @CreatedDate
    val createdAt: Instant = Instant.now(),

)
