package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.BaseVersionEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseVersionJpaRepository : CoroutineCrudRepository<BaseVersionEntity, Long>
