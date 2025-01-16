package me.apella.chat.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val lastSeen: LocalDateTime?,
    @OneToMany()
    val chatsAsSender: List<Chat> = mutableListOf(),
    @OneToMany()
    val chatsAsRecipient: List<Chat> = mutableListOf(),
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: LocalDateTime,
    @LastModifiedDate
    @Column(name = "last_modified", insertable = false)
    val lastModifiedDate: LocalDateTime
) {
    fun isOnline(): Boolean {
        return lastSeen?.isAfter(LocalDateTime.now().minusMinutes(2)) == true
    }
}