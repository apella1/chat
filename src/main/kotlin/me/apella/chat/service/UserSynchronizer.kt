package me.apella.chat.service

import me.apella.chat.extension.fromTokenAttributes
import me.apella.chat.repository.UserRepository
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class UserSynchronizer (val userRepository: UserRepository) {
    fun synchronizeWithIdp(token: Jwt) {
        val userEmail = getUserEmail(token)
        val optUser = userRepository.findUserByEmail(userEmail.toString())
        val user = optUser?.fromTokenAttributes(token.claims)
    }

    private fun getUserEmail(token: Jwt): String? {
        val attributes = token.claims
        return attributes["email"]?.toString()
    }
}