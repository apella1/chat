package me.apella.chat.service

import me.apella.chat.dto.UserResponse
import me.apella.chat.extension.toResponseDTO
import me.apella.chat.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun findAllUsersExceptSelf(connectedUser: Authentication): List<UserResponse> {
        val userId = connectedUser.name ?: throw IllegalArgumentException("User ID cannot be null!")
        return try {
            userRepository.findAllUsersExceptSelf(userId).map {
                it.toResponseDTO()
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch users: ", e)
        }

    }
}
