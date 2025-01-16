package me.apella.chat.extension

import me.apella.chat.dto.UserResponse
import me.apella.chat.entity.User

fun User.toResponseDTO(): UserResponse {
    return UserResponse(
        id = this.id.toString(),
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        lastSeen = this.lastSeen!!,
        isOnline = this.isOnline()
    )
}