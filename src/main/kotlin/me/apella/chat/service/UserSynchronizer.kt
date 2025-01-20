package me.apella.chat.service

import jakarta.transaction.Transactional
import me.apella.chat.extension.fromTokenAttributes
import me.apella.chat.repository.UserRepository
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class UserSynchronizer(val userRepository: UserRepository) {
    @Transactional
    fun synchronizeWithIdp(token: Jwt) {
        val userEmail = getUserEmail(token) ?: throw IllegalArgumentException("Email not found in token!")
        val optUser = userRepository.findUserByEmail(userEmail)
        val user = optUser?.fromTokenAttributes(token.claims)
            ?: throw IllegalArgumentException("User not found for email: $userEmail")
        userRepository.save(user)
    }

    private fun getUserEmail(token: Jwt): String? {
        val attributes = token.claims
        return attributes["email"]?.toString()
    }
}