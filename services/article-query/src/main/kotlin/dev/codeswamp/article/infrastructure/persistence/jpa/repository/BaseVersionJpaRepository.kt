package dev.codeswamp.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.article.infrastructure.persistence.jpa.entity.BaseVersionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseVersionJpaRepository : JpaRepository<BaseVersionEntity, Long>
