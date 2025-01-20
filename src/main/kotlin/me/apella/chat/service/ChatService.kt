package me.apella.chat.service

import jakarta.transaction.Transactional
import me.apella.chat.dto.ChatResponse
import me.apella.chat.extension.toResponseDTO
import me.apella.chat.extension.toUUID
import me.apella.chat.repository.ChatRepository
import me.apella.chat.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ChatService(val chatRepository: ChatRepository, val userRepository: UserRepository) {
    @Transactional
    fun getChatsBySenderId(currentUser: Authentication): List<ChatResponse> {
        val userId = currentUser.name ?: throw IllegalArgumentException("User Id is required!")
        val userUUID = userId.toUUID() ?: throw java.lang.IllegalArgumentException("Invalid UUID format for user Id!")
        return try {
            chatRepository.findChatsBySenderId(userUUID).map { it.toResponseDTO(userId) }
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch chats: ", e)
        }
    }
}
