package dev.codeswamp.core.article.infrastructure.cache

import dev.codeswamp.core.article.application.cache.FolderDeletionCache
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter

@Component
class RedisFolderDeletionCache(
    @Qualifier("folderDeletionCache") private val redisTemplate: RedisTemplate<String, String>,
) : FolderDeletionCache {
    private val prefix = "folder_deletion"
    private val duration = Duration.ofMinutes(10)

    override fun markAsDeleted(folders: List<Long>, deletedAt: Instant) {
        val deletedAtAsString = DateTimeFormatter.ISO_INSTANT.format(deletedAt)

        redisTemplate.executePipelined { connection ->
            val stringSerializer = redisTemplate.stringSerializer
            val stringCommands = connection.stringCommands()
            val value = stringSerializer.serialize(deletedAtAsString) ?:
                throw IllegalArgumentException("Could not serialize deleted into Redis!")//TODO

            folders.forEach { folderId ->
                val key = stringSerializer.serialize("$prefix:$folderId") ?: return@forEach//TODO
                stringCommands.setEx(key,  duration.seconds, value)
            }

            null
        }
    }

    override fun isDeleted(folderId: Long): Boolean {
        val key = "$prefix:$folderId"
        return redisTemplate.opsForValue().get(key) != null
    }

    override fun removeDeletedMark(folders: List<Long>) {
        val keys = folders.map {"$prefix:$it"}
        redisTemplate.delete(keys)
    }
}