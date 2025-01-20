package me.apella.chat.extension

import me.apella.chat.dto.ChatResponse
import me.apella.chat.dto.MessageResponse
import me.apella.chat.dto.UserResponse
import me.apella.chat.entity.Chat
import me.apella.chat.entity.Message
import me.apella.chat.entity.User
import me.apella.chat.util.FileUtils
import java.time.LocalDateTime
import java.util.UUID

fun User.toResponseDTO(): UserResponse {
    return UserResponse(
        id = this.id.toString(),
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        lastSeen = this.lastSeen ?: LocalDateTime.now(),
        isOnline = this.isOnline()
    )
}

fun User.fromTokenAttributes(attributes: Map<String, Any>): User {
    this.id = attributes["sub"]?.toString()?.let {
        UUID.fromString(it)
    } ?: throw IllegalArgumentException("Required field 'sub' is missing or invalid!")

    this.firstName =
        attributes["given_name"]?.toString() ?: attributes["nickname"]?.toString()
                ?: throw IllegalArgumentException("Required field 'given_name' or 'nickname' is missing!")

    this.lastName = attributes["family_name"]?.toString()
        ?: throw IllegalArgumentException("Required property 'family_name is missing!")

    this.email = attributes["email"]?.toString() ?: throw IllegalArgumentException("Required field 'email' is missing!")

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


fun Chat.toResponseDTO(senderId: String): ChatResponse {
    val senderUUID = senderId.toUUID() ?: throw IllegalArgumentException("Invalid value provided for sender Id!")
    return ChatResponse(
        id = this.id.toString(),
        name = this.getChatName(senderId),
        unreadCount = this.getUnreadMessages(senderUUID).toLong(),
        lastMessage = this.getLastMessage()?.toString() ?: "",
        isRecipientOnline = this.recipient.isOnline(),
        senderId = sender.id.toString(),
        receiverId = recipient.id.toString(),
        lastMessageTime = this.getLastMessageTime()
    )
}