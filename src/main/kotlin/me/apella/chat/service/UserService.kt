package me.apella.chat.service

import me.apella.chat.dto.UserResponse
import me.apella.chat.extension.toResponseDTO
import me.apella.chat.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun findAllUsersExceptSelf(connectedUser: Authentication): List<UserResponse> {
        return userRepository.findAllUsersExceptSelf(connectedUser.name).map {
            it.toResponseDTO()
        }
    }
}
