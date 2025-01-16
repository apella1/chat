package me.apella.chat.extension

import java.util.UUID

fun Any.toUUID(): UUID? {
    return when(this) {
        is String -> try {
            UUID.fromString(this)
        } catch (e: IllegalArgumentException) {
            null
        }
        else -> null
    }
}