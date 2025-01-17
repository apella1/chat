package me.apella.chat.service

import jakarta.transaction.Transactional
import me.apella.chat.dto.ChatResponse
import me.apella.chat.extension.toResponseDTO
import me.apella.chat.extension.toUUID
import me.apella.chat.repository.ChatRepository
import me.apella.chat.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(val chatRepository: ChatRepository, val userRepository: UserRepository) {
    @Transactional
    fun getChatsByReceiverId(currentUser: Authentication): List<ChatResponse> {
        val userId = currentUser.name ?: throw IllegalArgumentException("User Id is required!")
        return try {
            chatRepository.findChatsBySenderId(userId.toUUID()!!).map { it.toResponseDTO(userId) }
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch chats: ", e)
        }
    }

    fun createChat(senderId: UUID, receiverId: UUID): String? {
        val existingChat = chatRepository.findChatsBySenderIdAndRecipientId(senderId, receiverId)
        val existingChatId = existingChat?.id
        return existingChatId?.toString()
    }
}
