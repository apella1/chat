package me.apella.chat.dto

import java.time.LocalDateTime
import java.util.UUID

data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val lastSeen: LocalDateTime,
    val isOnline: Boolean
)
