package me.apella.chat.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class CustomException(
    val timestamp: LocalDateTime,
    val status: HttpStatus,
    val message: String
)