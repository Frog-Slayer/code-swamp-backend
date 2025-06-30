package dev.codeswamp.framework.infrastructure.support

import java.time.Instant

object SnowflakeIdGenerator {
    private const val EPOCH = 1748790000
    private const val WORKER_ID_BITS = 10L
    private const val SEQUENCE_BITS = 12L

    private val MAX_WORKER_ID = -1L xor (-1L shl WORKER_ID_BITS.toInt())
    private val MAX_SEQUENCE = -1L xor (-1L shl SEQUENCE_BITS.toInt())

    private val WORKER_ID_SHIFT = SEQUENCE_BITS
    private val TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS

    private var workerId: Long = 0
    private var sequence = 0L
    private var lastTimestamp = -1L

    fun setWorkerId(id: Long) {
        require(id in 0..MAX_WORKER_ID) { "Worker ID out of range" }
        workerId = id
    }

    @Synchronized
    fun generateId(): Long {
        var timestamp = timeGen()
        if (timestamp < lastTimestamp) {
            throw RuntimeException("Clock moved backwards. Refusing to generate id")
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) and MAX_SEQUENCE
            if (sequence == 0L) {
                timestamp = waitNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0
        }

        lastTimestamp = timestamp

        return ((timestamp - EPOCH) shl TIMESTAMP_SHIFT.toInt()) or
                (workerId shl WORKER_ID_SHIFT.toInt()) or
                sequence
    }

    private fun waitNextMillis(lastTimestamp: Long): Long {
        var timestamp = timeGen()
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen()
        }
        return timestamp
    }

    private fun timeGen(): Long = Instant.now().toEpochMilli()
}