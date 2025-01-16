package me.apella.chat.extension

import me.apella.chat.dto.MessageResponse
import me.apella.chat.dto.UserResponse
import me.apella.chat.entity.Message
import me.apella.chat.entity.User
import me.apella.chat.util.FileUtils
import java.time.LocalDateTime

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

fun User.fromTokenAttributes(attributes: Map<String, Object>): User {
    if (attributes.containsKey("sub")) {
        this.id = attributes["sub"]?.toUUID()!!
    }

    if (attributes.containsKey("given_name")) {
        this.firstName = attributes["given_name"].toString()
    } else if (attributes.containsKey("nickname")) {
        this.firstName = attributes["nickname"].toString()
    }

    if (attributes.containsKey("family_name")) {
        this.lastName = attributes["family_name"].toString()
    }

    if (attributes.containsKey("email")) {
        this.email = attributes["email"].toString()
    }

    this.lastSeen = LocalDateTime.now()
    return this
}


fun Message.toResponseDTO(): MessageResponse {
    return MessageResponse(
        id = this.id,
        content = this.content,
        type = this.type,
        senderId = this.senderId,
        receiverId = this.receiverId,
        createdAt = this.createdAt,
        media = FileUtils().readFileFromLocation(this.mediaFilePath.toString()),
        state = this.state
    )
}