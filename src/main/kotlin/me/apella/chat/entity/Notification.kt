package me.apella.chat.entity

import me.apella.chat.enums.NotificationType
import java.util.UUID

data class Notification @OptIn(ExperimentalUnsignedTypes::class) constructor(
    val chatId: UUID,
    val content: String,
    val senderId: UUID,
    val receiverId: UUID,
    val chatName: String,
    val messageType: Message,
    val type: NotificationType,
    val media: UByteArray
) {
    @OptIn(ExperimentalUnsignedTypes::class)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notification

        if (chatId != other.chatId) return false
        if (content != other.content) return false
        if (senderId != other.senderId) return false
        if (receiverId != other.receiverId) return false
        if (chatName != other.chatName) return false
        if (messageType != other.messageType) return false
        if (type != other.type) return false
        if (!media.contentEquals(other.media)) return false

        return true
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun hashCode(): Int {
        var result = chatId.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + senderId.hashCode()
        result = 31 * result + receiverId.hashCode()
        result = 31 * result + chatName.hashCode()
        result = 31 * result + messageType.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + media.contentHashCode()
        return result
    }
}