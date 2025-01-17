package me.apella.chat.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var firstName: String,
    var lastName: String,
    var email: String,
    var lastSeen: LocalDateTime?,
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    val chatsAsSender: Set<Chat> = mutableSetOf(),
    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    val chatsAsRecipient: Set<Chat> = mutableSetOf(),
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: LocalDateTime,
    @LastModifiedDate
    @Column(name = "last_modified", insertable = false)
    val lastModifiedDate: LocalDateTime
) {
    companion object {
        const val ONLINE_THRESHOLD_MINUTES = 2L
    }

    fun isOnline(): Boolean {
        val now = LocalDateTime.now()
        return lastSeen?.plusMinutes(ONLINE_THRESHOLD_MINUTES)?.isAfter(now) == true
    }
}