package me.apella.chat.repository

import me.apella.chat.entity.Chat
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ChatRepository: JpaRepository<Chat, UUID> {
    fun findChatsBySenderId(senderId: UUID): List<Chat>
    fun findChatsBySenderIdAndRecipientId(senderId: UUID, recipientId: UUID): Chat?
}