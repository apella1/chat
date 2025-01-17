package me.apella.chat.extension

import org.slf4j.LoggerFactory
import java.util.UUID

private val logger = LoggerFactory.getLogger("UUIDUtils")

fun Any.toUUID(): UUID? {
    return when(this) {
        is String -> try {
            UUID.fromString(this)
        } catch (e: IllegalArgumentException) {
            logger.debug("Failed to convert string to UUID: $this", e)
            null
        }
        else -> null
    }
}