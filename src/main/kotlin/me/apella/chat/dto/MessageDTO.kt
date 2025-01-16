package me.apella.chat.dto

import me.apella.chat.enums.MessageState
import me.apella.chat.enums.MessageType
import java.time.LocalDateTime
import java.util.UUID

data class MessageResponse(
    val id: UUID,
    val content: String,
    val type: MessageType,
    val state: MessageState,
    val senderId: UUID,
    val receiverId: UUID,
    val createdAt: LocalDateTime,
    val media: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageResponse

        if (id != other.id) return false
        if (content != other.content) return false
        if (type != other.type) return false
        if (state != other.state) return false
        if (senderId != other.senderId) return false
        if (receiverId != other.receiverId) return false
        if (createdAt != other.createdAt) return false
        if (!media.contentEquals(other.media)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + state.hashCode()
        result = 31 * result + senderId.hashCode()
        result = 31 * result + receiverId.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (media?.contentHashCode() ?: 0)
        return result
    }
}

data class MessageRequest(
    val content: String,
    val senderId: String,
    val receiverId: String,
    val type: MessageType,
    val chatId: String
)